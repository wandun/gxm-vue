import request from '@/utils/request'

export function getList() {
  return request({
    url: `/api/storage/qiniu/list`,
    method: 'get'
  })
}

export function del(name) {
  return request({
    url: `/api/storage/qiniu/delete?name=${name}`,
    method: 'delete'
  })
}

export function update(oldname, newname) {
  return request({
    url: `/api/storage/qiniu/update?oldname=${oldname}&newname=${newname}`,
    method: 'get'
  })
}

export function download(name) {
  return request({
    url: `/api/storage/qiniu/download?name=${name}`,
    method: 'get'
  })
}
