import { defineStore } from 'pinia';
import { register, sendCode } from '../api/user';

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    refreshToken: localStorage.getItem('refreshToken') || '',
    userInfo: JSON.parse(localStorage.getItem('userInfo') || '{}'),
  }),

  actions: {
    async sendVerificationCode(phone) {
      const res = await sendCode(phone);
      return res;
    },

    async register(phone, code, password) {
      const res = await register({ phone, code, password });
      if (res.data) {
        this.token = res.data.token;
        this.refreshToken = res.data.refreshToken;
        this.userInfo = res.data.user;

        localStorage.setItem('token', this.token);
        localStorage.setItem('refreshToken', this.refreshToken);
        localStorage.setItem('userInfo', JSON.stringify(this.userInfo));
      }
      return res;
    },

    logout() {
      this.token = '';
      this.refreshToken = '';
      this.userInfo = {};
      localStorage.removeItem('token');
      localStorage.removeItem('refreshToken');
      localStorage.removeItem('userInfo');
    },

    isLoggedIn() {
      return !!this.token;
    },
  },
});
