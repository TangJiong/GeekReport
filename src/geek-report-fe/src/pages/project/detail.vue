<template>
  <div class="main-container">
    <div class="container-body">
      <h2 class="project-title">{{ project.title }}</h2>
      <div class="divider"></div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import {
  ProjectService
} from '@/services'

export default {
  data () {
    return {
      project: {
        id: '',
        title: ''
      }
    }
  },

  created () {
    this.init()
  },

  methods: {
    init () {
      this.initProject()
    },

    initProject () {
      let projectId = this.$route.params.projectId
      let vm = this
      ProjectService.getById(projectId).then(({data}) => {
        vm.project = data
      })
    }
  },

  watch: {
    '$route': 'init'
  }
}
</script>

<style scoped>
.project-title {
  text-align: center;
}
</style>
