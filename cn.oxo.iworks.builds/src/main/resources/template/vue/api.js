import request from '@/utils/request'
import {switchForm} from '@/api/iunits'


export function list(params) {
  return request({
    url:'${TableBean.action}/list',
    method:'POST',
    data:switchForm(params)
  })
}

export function saveUpdate(params) {
  return request({
    url:'${TableBean.action}/save',
    method:'POST',
    data:switchForm(params)
  })
}

export function get(params) {
  return request({
    url:'${TableBean.action}/get',
    method:'POST',
    data:switchForm(params)
  })
}

export function delete(params) {
  return request({
    url:'${TableBean.action}/delete',
    method:'POST',
    data:switchForm(params)
  })
}