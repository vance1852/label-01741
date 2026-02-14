import request from './request'

// 认证
export const login = data => request.post('/auth/login', data)
export const getUserInfo = () => request.get('/auth/info')
export const logout = () => request.post('/auth/logout')

// 仪表盘
export const getDashboardStats = () => request.get('/dashboard/stats')

// 老人管理
export const getElderPage = params => request.get('/elder/page', { params })
export const getElderList = () => request.get('/elder/list')
export const getElderById = id => request.get(`/elder/${id}`)
export const saveElder = data => request.post('/elder', data)
export const updateElder = data => request.put('/elder', data)
export const deleteElder = id => request.delete(`/elder/${id}`)

// 护工管理
export const getWorkerPage = params => request.get('/worker/page', { params })
export const getWorkerList = () => request.get('/worker/list')
export const saveWorker = data => request.post('/worker', data)
export const updateWorker = data => request.put('/worker', data)
export const deleteWorker = id => request.delete(`/worker/${id}`)

// 服务管理
export const getServicePage = params => request.get('/service/page', { params })
export const getServiceList = () => request.get('/service/list')
export const saveService = data => request.post('/service', data)
export const updateService = data => request.put('/service', data)
export const deleteService = id => request.delete(`/service/${id}`)

// 订单管理
export const getOrderPage = params => request.get('/order/page', { params })
export const saveOrder = data => request.post('/order', data)
export const updateOrderStatus = data => request.put('/order/status', data)

// 健康管理
export const getHealthPage = params => request.get('/health/page', { params })
export const getHealthByElder = elderId => request.get(`/health/elder/${elderId}`)
export const saveHealth = data => request.post('/health', data)

// 日志
export const getLogPage = params => request.get('/log/page', { params })
