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
        const headerNav = document.getElementById('auth-nav');
        if (!headerNav) {
            console.warn('Header navigation element not found');
            return;
        }

        // 헤더 메뉴 업데이트
        if (this.currentUser) {
            headerNav.innerHTML = `<div class="user-menu"><div class="user-id"><i class="material-icons">person</i>${this.currentUser}</div><a href="javascript:void(0)" class="logout-text" onclick="auth.logout()">${i18n.getTranslation('auth.logout')}</a></div>`;
        } else {
            headerNav.innerHTML = `<a href="login.html" class="logout-text">${i18n.getTranslation('auth.login')}</a>`;
        }

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
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                credentials: 'include',
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                const data = await response.json();
                this.currentUser = data.username;
                this.currentRole = data.role;
                this.updateUI();
                return true;
            } else {
                const error = await response.json();
                throw new Error(error.error || 'Login failed');
            }
        } catch (error) {
            console.error('Login error:', error);
            throw error;
        }
    },

    // Handle logout
    async logout() {
        try {
            const response = await fetch('/api/auth/logout', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json'
                },
                credentials: 'include'
            });

            if (response.ok) {
                this.currentUser = null;
                this.currentRole = null;
                this.updateUI();
                window.location.href = 'index.html';
            } else {
                throw new Error('Logout failed');
            }
        } catch (error) {
            console.error('Logout error:', error);
            alert(i18n.getTranslation('error.logout'));
        }
    },

    // Check session status
    async checkSession() {
        try {
            const response = await fetch('/api/auth/check', {
                method: 'GET',
                headers: {
                    'Accept': 'application/json'
                },
                credentials: 'include'
            });

            if (response.ok) {
                const data = await response.json();
                this.currentUser = data.username;
                this.currentRole = data.role;
                this.updateUI();
                return true;
            } else {
                this.currentUser = null;
                this.currentRole = null;
                this.updateUI();
                return false;
            }
        } catch (error) {
            console.error('Session check error:', error);
            this.currentUser = null;
            this.currentRole = null;
            this.updateUI();
            return false;
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