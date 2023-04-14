import request from '@/utils/request'

// 查询购物车列表
export function listShopCart(query) {
  return request({
    url: '/system/ShopCart/list',
    method: 'get',
    params: query
  })
}

// 查询购物车详细
export function getShopCart(shopCartId) {
  return request({
    url: '/system/ShopCart/' + shopCartId,
    method: 'get'
  })
}

// 新增购物车
export function addShopCart(data) {
  return request({
    url: '/system/ShopCart',
    method: 'post',
    data: data
  })
}

// 修改购物车
export function updateShopCart(data) {
  return request({
    url: '/system/ShopCart',
    method: 'put',
    data: data
  })
}

// 删除购物车
export function delShopCart(shopCartId) {
  return request({
    url: '/system/ShopCart/' + shopCartId,
    method: 'delete'
  })
}

// 导出购物车
export function exportShopCart(query) {
  return request({
    url: '/system/ShopCart/export',
    method: 'get',
    params: query
  })
}