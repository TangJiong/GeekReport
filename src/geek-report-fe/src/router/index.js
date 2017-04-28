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
              path: ':projectId',
              name: 'project-detail',
              component: require('@/pages/project/detail'),
              children: [
                {
                  path: 'page',
                  name: 'page',
                  component: require('@/pages/subject/subject'),
                  children: [
                    {
                      path: ':pageId',
                      name: 'page-detail',
                      component: require('@/pages/page/page')
                    }
                  ]
                }
              ]
            },
            {
              path: ':projectId/setting',
              name: 'project-setting',
              component: require('@/pages/project/setting')
            },
            {
              path: ':projectId/edit',
              name: 'project-edit',
              component: require('@/pages/project/form')
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
