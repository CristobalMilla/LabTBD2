import { ref, computed } from 'vue';

const user = ref(JSON.parse(localStorage.getItem('user')) || null);

export const useAuthStore = () => {
    const isAuthenticated = computed(() => !!user.value);
    const userId = computed(() => user.value ? user.value.id_usuario : null);
    const userToken = computed(() => user.value ? user.value.token : null);

    const setUser = (userData) => {
        user.value = userData;
        localStorage.setItem('user', JSON.stringify(userData));
    };

    const logout = () => {
        user.value = null;
        localStorage.removeItem('user');
    };

    const checkAuth = () => {
        const storedUser = localStorage.getItem('user');
        if (storedUser) {
            user.value = JSON.parse(storedUser);
        }
    };

    return {
        user,
        isAuthenticated,
        userId,
        userToken,
        setUser,
        logout,
        checkAuth
    };
}; 