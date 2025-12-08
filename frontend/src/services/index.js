import api from './api';

// Auth Services
export const authService = {
  register: (data) => api.post('/auth/register', data),
  login: (data) => api.post('/auth/login', data),
  bulkRegister: (data) => api.post('/auth/bulk/register', data),
};

// Asset Services
export const assetService = {
  getAll: () => api.get('/assets'),
  create: (data) => api.post('/assets', data),
  bulkCreate: (data) => api.post('/assets/bulk', data),
  update: (id, data) => api.put(`/assets/${id}`, data),
  partialUpdate: (id, data) => api.patch(`/assets/${id}`, data),
  delete: (id) => api.delete(`/assets/${id}`),
  search: (params) => api.get('/assets/search', { params }),
  getAssetTracks: (assetId) => api.get(`/assets/${assetId}/tracks`),
};

// Employee Services
export const employeeService = {
  getAll: (params) => api.get('/emps/all', { params }),
  getEmployeeTracks: (empId) => api.get(`/emps/${empId}/tracks`),
  countEmployeeTracks: (empId) => api.get(`/emps/count/${empId}/tracks`),
};

// Track Services
export const trackService = {
  assignAsset: (data) => api.post('/tracks/assignAsset', data),
  returnAsset: (data) => api.post('/tracks/returnAsset', data),
  search: (params) => api.get('/tracks/search', { params }),
  update: (trackId, data) => api.put(`/tracks/${trackId}`, data),
  partialUpdate: (trackId, data) => api.patch(`/tracks/${trackId}`, data),
  delete: (trackId) => api.delete(`/tracks/${trackId}`),
  getAnalytics: () => api.get('/tracks/analytics'),
};
