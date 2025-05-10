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
                    <span class="user-name">${user.username}</span>
                    <button onclick="auth.logout()" data-i18n="auth.logout">로그아웃</button>
                </div>
            `;
        } else {
            authNav.innerHTML = `
                <button onclick="location.href='login.html'" data-i18n="auth.login">로그인</button>
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
            localStorage.setItem('token', data.token);
            localStorage.setItem('user', JSON.stringify(data.user));
            this.currentUser = data.user.username;
            
            // Update UI and redirect
            this.updateUI();
            window.location.href = 'index.html';
        } catch (error) {
            console.error('Login error:', error);
            throw error;
        }
    },

    // Handle logout
    logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        this.currentUser = null;
        this.updateUI();
        window.location.href = 'login.html';
    },

    // Check session status
    async checkSession() {
        const token = localStorage.getItem('token');
        if (!token) {
            this.currentUser = null;
            this.updateUI();
            return;
        }

        try {
            const response = await fetch('/api/auth/check', {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
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