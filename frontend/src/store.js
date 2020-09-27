import Vue from 'vue'
import Vuex from 'vuex'
import http from './util/http-common'
import router from './router'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo: null,
    isLogin: false,
    articles: []
  },
  // state get
  getters: {
    isSignin(state) {
      return state.isLogin
    },
    articles(state) {
      return state.articles
    }
  },
  // state 변경
  mutations: {
    SigninSuccess(state, payload) {
      state.isLogin = true
      state.userInfo = payload
    },
    Signout(state) {
      state.isLogin = false
      state.userInfo = null
    }
  },
  actions: {
    onSignin({ commit }, payload) {
      http
        .post('/user/login', payload, null)
        .then((res) => {
          console.log(res)
          commit('SigninSuccess', res.data.user)
          router.push({ name: 'ArticleList' })

          const token = res.data.token
          localStorage.setItem('token', token)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    onSignout({ commit }) {
      commit('Signout')
      localStorage.removeItem('token')
    },
    onSignup({ commit }, payload) {
      http
        .post('/user/signup', payload, null)
        .then((res) => {
          console.log(res)
          router.push({ name: 'ArticleList' })
        })
        .catch((err) => {
          console.log(err)
        })
    },
    getUserInfo({ commit }) {
      const token = localStorage.getItem('token')
      if (token) {
        http
          .post('/user/info', { token: token }, null)
          .then((res) => {
            console.log(res)
            commit('SigninSuccess', res.data.user.user)
          })
          .catch((err) => {
            console.log(err)
          })
      }
    },
    createArticle(context, payload) {
      payload.userIdx = this.state.userInfo.idx
      http
        .post('/article/save', payload, null)
        .then((res) => {
          console.log(res)
          // router.push({ name: 'ArticleList' })
          router.push(`articles?articleIdx=${res.data.idx}`)
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }
})
