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
              path: 'new',
              name: 'project-new',
              component: require('@/pages/project/form')
            },
            {
              path: ':projectId/setting',
              name: 'project-setting',
              component: require('@/pages/project/form')
            },
            {
              path: ':projectId',
              name: 'project-detail',
              component: require('@/pages/project/detail'),
              redirect: {name: 'project-view'},
              children: [
                {
                  path: 'view',
                  name: 'project-view',
                  component: require('@/pages/page/page')
                },
                {
                  path: 'edit',
                  name: 'project-edit',
                  component: require('@/pages/page/page')
                }
              ]
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
