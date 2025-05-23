// Authentication state management
const auth = {
    currentUser: null,
    currentRole: null,

    // Check if user is logged in
    async isLoggedIn() {
        const token = localStorage.getItem('token');
        if (!token) return false;
    
        try {
            const res = await fetch('/api/auth/check', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                credentials: 'include'
            });
            return res.ok;
        } catch (err) {
            return false;
        }
    },    

    // Check if user is admin
    isAdmin() {
        return this.currentRole === 'ROLE_ADMIN';
    },

    // Check if current page requires authentication
    requiresAuth() {
        const currentPath = window.location.pathname;
        return currentPath.includes('new-order.html') || currentPath.includes('statistics.html');
    },

    // Redirect to login if not authenticated
    checkAuthAndRedirect() {
        if (this.requiresAuth() && !this.isLoggedIn()) {
            window.location.href = 'login.html';
        }
    },

    // Update UI based on authentication state
    updateUI() {
        const authNav = document.getElementById('auth-nav');
        if (!authNav) {
            console.warn('Auth navigation element not found');
            return;
        }
    
        const userRaw = localStorage.getItem('user');
        const user = userRaw ? JSON.parse(userRaw) : null;
    
        if (this.isLoggedIn() && user) {
            authNav.innerHTML = `
                <div class="user-menu">
                    <i class="material-icons" style="vertical-align: middle; color: #1da1f2;">person</i>
                    <span class="user-name">${user.username}</span>
                    <button class="menu-btn" onclick="auth.logout()" data-i18n="auth.logout">로그아웃</button>
                </div>
            `;
        } else {
            authNav.innerHTML = `
                <button class="menu-btn" onclick="location.href='login.html'" data-i18n="auth.login">로그인</button>
            `;
        }
    
        i18n.updateTexts();
    
        document.dispatchEvent(new CustomEvent('authStateChanged', {
            detail: { isLoggedIn: this.isLoggedIn(), isAdmin: this.isAdmin() }
        }));
    },

    // Handle login
    async login(username, password) {
        try {
            const response = await fetch('/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });

            if (!response.ok) {
                throw new Error('Login failed');
            }

            const data = await response.json();
            let userObj = null;
            if (data.user && data.user.username) {
                userObj = data.user;
                this.currentUser = data.user.username;
            } else if (data.username) {
                userObj = { username: data.username };
                this.currentUser = data.username;
            } else {
                alert('서버 응답에 사용자 정보가 없습니다.');
                return;
            }
            localStorage.setItem('token', data.token);
            localStorage.setItem('user', JSON.stringify(userObj));

            // Update UI and redirect
            this.updateUI();
            window.location.href = 'index.html';
        } catch (error) {
            console.error('Login error:', error);
            alert('로그인에 실패했습니다. 아이디/비밀번호를 확인하세요.');
        }
    },

    // Handle logout
    logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        this.currentUser = null;
        this.updateUI();
        window.location.href = 'index.html';
    },

    // Check session status
    async checkSession() {
        const token = localStorage.getItem('token');
        const userRaw = localStorage.getItem('user');
    
        if (!token || !userRaw) {
            console.warn('No token or user info found');
            this.currentUser = null;
            this.updateUI();
            return;
        }
    
        const user = JSON.parse(userRaw);
        this.currentUser = user.username;
    
        try {
            const response = await fetch('/api/auth/check', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                credentials: 'include'
            });
    
            if (!response.ok) {
                throw new Error('Session invalid');
            }
    
            const data = await response.json();
            this.currentUser = data.username;
        } catch (error) {
            console.error('Session check error:', error);
            this.logout(); // 토큰 만료 등
        } finally {
            this.updateUI(); // 항상 UI는 갱신되도록
        }
    }    
};

// Check authentication on page load
document.addEventListener('DOMContentLoaded', () => {
    auth.checkSession().then(() => {
        auth.checkAuthAndRedirect();
    });
});

// Update auth UI when language changes
document.addEventListener('languageChanged', () => {
    auth.updateUI();
}); 