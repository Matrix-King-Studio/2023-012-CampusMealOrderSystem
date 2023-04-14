import request from '@/utils/request'

// 查询订单管理 列表
export function listOrderInfo(query) {
  return request({
    url: '/system/OrderInfo/list',
    method: 'get',
    params: query
  })
}

// 查询订单管理 详细
export function getOrderInfo(orderId) {
  return request({
    url: '/system/OrderInfo/' + orderId,
    method: 'get'
  })
}

// 新增订单管理 
export function addOrderInfo(data) {
  return request({
    url: '/system/OrderInfo',
    method: 'post',
    data: data
  })
}

// 修改订单管理 
export function updateOrderInfo(data) {
  return request({
    url: '/system/OrderInfo',
    method: 'put',
    data: data
  })
}

// 删除订单管理 
export function delOrderInfo(orderId) {
  return request({
    url: '/system/OrderInfo/' + orderId,
    method: 'delete'
  })
}

// 导出订单管理 
export function exportOrderInfo(query) {
  return request({
    url: '/system/OrderInfo/export',
    method: 'get',
    params: query
  })
}