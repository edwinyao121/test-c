import apiClient from '../utils/request';

export const sendCode = (phone, type = 'REGISTER') => {
  return apiClient.post('/users/send-code', { phone, type });
};

export const register = (data) => {
  return apiClient.post('/users/register', data);
};
