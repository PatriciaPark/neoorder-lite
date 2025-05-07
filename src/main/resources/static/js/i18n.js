// 번역 데이터
const translations = {
    ko: {
        'menu.orders': '주문 관리',
        'menu.statistics': '통계',
        'nav.orders': '주문 목록',
        'nav.statistics': '통계',
        'nav.language': '언어',
        'nav.language.ko': '한국어',
        'nav.language.en': '영어',
        'auth.login': '로그인',
        'auth.logout': '로그아웃',
        'auth.username': '아이디',
        'auth.password': '비밀번호',
        'auth.error': '아이디 또는 비밀번호가 올바르지 않습니다.',
        'auth.title': '관리자 로그인',
        'order.list.title': '주문 목록',
        'order.create.title': '주문 생성',
        'order.status.all': '전체',
        'order.status.received': '접수됨',
        'order.status.shipping': '배송중',
        'order.status.in_progress': '처리중',
        'order.status.completed': '완료',
        'order.status.cancelled': '취소됨',
        'order.item': '상품',
        'order.customer.name': '고객명',
        'order.status': '상태',
        'order.created_at': '주문일시',
        'order.actions': '작업',
        'order.button.create': '주문 생성',
        'order.button.delete': '삭제',
        'order.button.status': '상태 변경',
        'order.button.search': '검색',
        'order.search.item': '상품명',
        'order.search.customer': '고객명',
        'order.search.status': '상태',
        'order.no.data': '주문 내역이 없습니다.',
        'button.cancel': '취소',
        'stats.title': '주문 통계',
        'stats.date.start': '시작일',
        'stats.date.end': '종료일',
        'stats.button.search': '조회',
        'stats.button.csv': 'CSV 다운로드',
        'stats.button.charts': '차트 저장',
        'stats.total.orders': '총 주문',
        'stats.completion.rate': '완료율',
        'stats.avg.time': '평균 처리 시간',
        'stats.unit.count': '건',
        'stats.unit.percent': '%',
        'stats.unit.minutes': '분',
        'stats.status.distribution': '주문 상태 분포',
        'stats.popular.items': '인기 상품 TOP 5',
        'stats.no.data': '선택한 기간에 해당하는 데이터가 없습니다.',
        'alert.date.invalid': '시작일은 종료일보다 이전이어야 합니다.',
        'alert.date.required': '시작일과 종료일을 모두 선택해주세요.',
        'alert.confirm.delete': '정말 삭제하시겠습니까?',
        'error.load.orders': '주문 목록을 불러오는데 실패했습니다.',
        'error.create.order': '주문 생성에 실패했습니다.',
        'error.update.status': '주문 상태 변경에 실패했습니다.',
        'error.delete.order': '주문 삭제에 실패했습니다.',
        'welcome.title': '환영합니다!',
        'welcome.description': 'NeoOrder Lite는 간단하고 효율적인 주문 관리 시스템입니다. 주문을 생성하고, 상태를 관리하며, 통계를 확인할 수 있습니다.',
        'welcome.start': '시작하기',
        'nav.home': '홈',
        'index.main.title': 'NeoOrder Lite에 오신 것을 환영합니다',
        'index.main.subtitle': '간단하고 효율적인 주문 관리 시스템',
        'index.card.orders.title': '주문 관리',
        'index.card.orders.description': '모든 주문을 조회, 필터링, 상태 변경 및 삭제할 수 있습니다.',
        'index.card.new.title': '주문 생성',
        'index.card.new.description': '새로운 주문을 생성하고 관리할 수 있습니다.',
        'index.card.stats.title': '주문 통계',
        'index.card.stats.description': '주문 데이터에 대한 통계 및 분석을 확인할 수 있습니다.'
    },
    en: {
        'menu.orders': 'Orders',
        'menu.statistics': 'Statistics',
        'nav.orders': 'Orders',
        'nav.statistics': 'Statistics',
        'nav.language': 'Language',
        'nav.language.ko': 'Korean',
        'nav.language.en': 'English',
        'auth.login': 'Login',
        'auth.logout': 'Logout',
        'auth.username': 'Username',
        'auth.password': 'Password',
        'auth.error': 'Invalid username or password.',
        'auth.title': 'Admin Login',
        'order.list.title': 'Order List',
        'order.create.title': 'Create Order',
        'order.status.all': 'All',
        'order.status.received': 'Received',
        'order.status.shipping': 'Shipping',
        'order.status.in_progress': 'In Progress',
        'order.status.completed': 'Completed',
        'order.status.cancelled': 'Cancelled',
        'order.item': 'Item',
        'order.customer.name': 'Customer Name',
        'order.status': 'Status',
        'order.created_at': 'Created At',
        'order.actions': 'Actions',
        'order.button.create': 'Create Order',
        'order.button.delete': 'Delete',
        'order.button.status': 'Change Status',
        'order.button.search': 'Search',
        'order.search.item': 'Item Name',
        'order.search.customer': 'Customer Name',
        'order.search.status': 'Status',
        'order.no.data': 'No orders found.',
        'button.cancel': 'Cancel',
        'stats.title': 'Order Statistics',
        'stats.date.start': 'Start Date',
        'stats.date.end': 'End Date',
        'stats.button.search': 'Search',
        'stats.button.csv': 'Download CSV',
        'stats.button.charts': 'Save Charts',
        'stats.total.orders': 'Total Orders',
        'stats.completion.rate': 'Completion Rate',
        'stats.avg.time': 'Avg. Processing Time',
        'stats.unit.count': 'orders',
        'stats.unit.percent': '%',
        'stats.unit.minutes': 'min',
        'stats.status.distribution': 'Order Status Distribution',
        'stats.popular.items': 'Top 5 Popular Items',
        'stats.no.data': 'No data available for the selected period.',
        'alert.date.invalid': 'Start date must be before end date.',
        'alert.date.required': 'Please select both start and end dates.',
        'alert.confirm.delete': 'Are you sure you want to delete this order?',
        'error.load.orders': 'Failed to load orders.',
        'error.create.order': 'Failed to create order.',
        'error.update.status': 'Failed to update order status.',
        'error.delete.order': 'Failed to delete order.',
        'welcome.title': 'Welcome!',
        'welcome.description': 'NeoOrder Lite is a simple and efficient order management system. Create orders, manage their status, and view statistics.',
        'welcome.start': 'Get Started',
        'nav.home': 'Home',
        'index.main.title': 'Welcome to NeoOrder Lite',
        'index.main.subtitle': 'Simple and Efficient Order Management System',
        'index.card.orders.title': 'Order Management',
        'index.card.orders.description': 'View, filter, update status, and delete all orders.',
        'index.card.new.title': 'Create Order',
        'index.card.new.description': 'Create and manage new orders.',
        'index.card.stats.title': 'Order Statistics',
        'index.card.stats.description': 'View statistics and analysis of order data.'
    }
};

// 현재 언어 설정
let currentLang = localStorage.getItem('language') || 'ko';

// 언어 변경 함수
function changeLang(lang) {
    currentLang = lang;
    localStorage.setItem('language', lang);
    updateTexts();
    // 커스텀 이벤트 발생
    const event = new CustomEvent('languageChanged', { detail: { language: lang } });
    document.dispatchEvent(event);
}

// 텍스트 업데이트 함수
function updateTexts() {
    document.querySelectorAll('[data-i18n]').forEach(element => {
        const key = element.getAttribute('data-i18n');
        if (translations[currentLang][key]) {
            if (element.tagName === 'INPUT' && element.type === 'button') {
                element.value = translations[currentLang][key];
            } else {
                element.textContent = translations[currentLang][key];
            }
        }
    });
}

// 번역 텍스트 가져오기 함수
function getTranslation(key) {
    return translations[currentLang][key] || key;
}

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', () => {
    updateTexts();
});

// 전역으로 노출할 함수들
window.i18n = {
    changeLang,
    updateTexts,
    getTranslation,
    getCurrentLang: () => currentLang
}; 