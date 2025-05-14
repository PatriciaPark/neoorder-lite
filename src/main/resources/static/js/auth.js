// Authentication state management
const auth = {
    currentUser: null,
    currentRole: null,

    // Check if user is logged in
    isLoggedIn() {
        return this.currentUser !== null;
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

        if (this.isLoggedIn()) {
            const user = JSON.parse(localStorage.getItem('user'));
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

        // Dispatch auth state change event
        document.dispatchEvent(new CustomEvent('authStateChanged', {
            detail: { isLoggedIn: this.isLoggedIn(), isAdmin: this.isAdmin() }
        }));
    },

    // Handle login
    async login(username, password) {
        try {
            console.log('Attempting login for user:', username);
            const response = await fetch('https://neoorder-lite.onrender.com/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include',
                body: JSON.stringify({ username, password })
            });

            let data;
            const textResponse = await response.text();
            console.log('Raw response:', textResponse);
            
            try {
                data = textResponse ? JSON.parse(textResponse) : {};
            } catch (e) {
                console.error('Failed to parse response:', textResponse);
                throw new Error('서버 응답을 처리할 수 없습니다.');
            }
            
            if (!response.ok) {
                console.error('Login failed:', {
                    status: response.status,
                    statusText: response.statusText,
                    error: data?.error,
                    details: data?.details
                });
                throw new Error(data?.error || '로그인에 실패했습니다.');
            }

            if (!data.token) {
                console.error('No token in response:', data);
                throw new Error('서버 응답에 토큰이 없습니다.');
            }

            if (!data.username && (!data.user || !data.user.username)) {
                console.error('No username in response:', data);
                throw new Error('서버 응답에 사용자 정보가 없습니다.');
            }

            // Store user data
            const userObj = data.user || { username: data.username };
            this.currentUser = userObj.username;
            localStorage.setItem('token', data.token);
            localStorage.setItem('user', JSON.stringify(userObj));

            console.log('Login successful:', { username: userObj.username });

            // Update UI and redirect
            this.updateUI();
            window.location.href = 'index.html';
        } catch (error) {
            console.error('Login error:', {
                message: error.message,
                stack: error.stack,
                name: error.name
            });
            
            let errorMessage;
            if (error.message.includes('서버 응답')) {
                errorMessage = error.message;
            } else if (error.name === 'TypeError') {
                errorMessage = '서버에 연결할 수 없습니다. 잠시 후 다시 시도해주세요.';
            } else {
                errorMessage = '로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.';
            }
            
            alert(errorMessage);
        }
    },

    // Handle logout
    async logout() {
        try {
            await fetch('https://neoorder-lite.onrender.com/api/auth/logout', {
                method: 'POST',
                credentials: 'include'
            });
        } catch (error) {
            console.error('Logout error:', error);
        }
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        this.currentUser = null;
        this.updateUI();
        window.location.href = 'login.html';
    },

    // Check session status
    async checkSession() {
        const token = localStorage.getItem('token');
        const user = localStorage.getItem('user');
        if (!token || !user) {
            this.currentUser = null;
            this.updateUI();
            return;
        }

        try {
            const response = await fetch('https://neoorder-lite.onrender.com/api/auth/check', {
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
            this.updateUI();
        } catch (error) {
            console.error('Session check error:', error);
            this.logout();
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