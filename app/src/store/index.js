import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import api from './modules/api'
import settings from './modules/settings'
import user from './modules/user'

Vue.use(Vuex)
/*
  * https://vuex.vuejs.org/zh/guide/
  * vuex 中的store就像一个 全局变量
  */


const store = new Vuex.Store({
  /*
  *  Vuex 允许我们将 store 分割成模块（module）。
  *  每个模块拥有自己的 state、mutation、action、getter、
  * */
  modules: {
    app,
    api,
    settings,
    user
  },
  getters
})

export default store
