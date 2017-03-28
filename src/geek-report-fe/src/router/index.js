import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: require('@/pages/index')
    },
    {
      path: '/:username',
      name: 'home',
      component: require('@/pages/home'),
      redirect: { name: 'project' },
      children: [
        {
          path: 'project',
          name: 'project',
          component: require('@/pages/project/project'),
          redirect: { name: 'project-list' },
          children: [
            {
              path: 'all',
              name: 'project-list',
              component: require('@/pages/project/list')
            },
            {
              path: ':projectId',
              name: 'project-detail',
              component: require('@/pages/project/detail')
            },
            {
              path: ':projectId/setting',
              name: 'project-setting',
              component: require('@/pages/project/setting')
            }
          ]
        },
        {
          path: 'profile',
          name: 'profile',
          component: require('@/pages/user/profile')
        },
        {
          path: 'setting',
          name: 'setting',
          component: require('@/pages/setting')
        }
      ]
    }

  ]
})
