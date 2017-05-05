// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import VueResource from 'vue-resource'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import filters from './filters'

Vue.use(VueResource)
Vue.use(ElementUI)
Vue.use(filters)

Vue.component('editor', require('./components/Editor'))

Vue.config.productionTip = false

Vue.http.options.root = '/api'

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
