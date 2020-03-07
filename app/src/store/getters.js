
// 箭头函数 https://juejin.im/post/5aa1eb056fb9a028b77a66fd

const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  lastLoginTime: state => state.user.lastLoginTime,
  name: state => state.user.name,
  qiNiuUploadApi: state => state.api.qiNiuUploadApi,
  swaggerApi: state => state.api.swaggerApi
}
export default getters
