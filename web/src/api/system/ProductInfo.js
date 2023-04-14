import request from '@/utils/request'

// 查询菜品管理列表
export function listProductInfo(query) {
  return request({
    url: '/system/ProductInfo/list',
    method: 'get',
    params: query
  })
}

// 查询菜品管理详细
export function getProductInfo(productId) {
  return request({
    url: '/system/ProductInfo/' + productId,
    method: 'get'
  })
}

// 新增菜品管理
export function addProductInfo(data) {
  return request({
    url: '/system/ProductInfo',
    method: 'post',
    data: data
  })
}

// 修改菜品管理
export function updateProductInfo(data) {
  return request({
    url: '/system/ProductInfo',
    method: 'put',
    data: data
  })
}

// 删除菜品管理
export function delProductInfo(productId) {
  return request({
    url: '/system/ProductInfo/' + productId,
    method: 'delete'
  })
}

// 导出菜品管理
export function exportProductInfo(query) {
  return request({
    url: '/system/ProductInfo/export',
    method: 'get',
    params: query
  })
}
