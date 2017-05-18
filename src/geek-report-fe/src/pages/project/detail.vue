<template>
  <div class="main-container">
    <div class="container-body">
      <h2 class="project-title">
        {{ project.title }}
        <el-switch
          v-model="editable"
          on-text="编辑"
          off-text="只读"
          @change="handleTypeChange">
        </el-switch>
      </h2>
      <div class="project-extra">
        <span class="extra-item">
          <i class="fa fa-user" aria-hidden="true"></i>
          {{ project.created_by }}
        </span>
        <span class="extra-item">
          <i class="fa fa-calendar" aria-hidden="true"></i>
          {{ project.created_at | toReadableTime }}
        </span>
      </div>
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
      },
      editable: false
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
    },

    handleTypeChange (editable) {
      if (editable) {
        this.$router.push({name: 'project-edit', params: {projectId: this.project.id}})
      } else {
        this.$router.push({name: 'project-view', params: {projectId: this.project.id}})
      }
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
  margin-bottom: 5px;
}
.project-title-menu {
  font-size: 14px;
  color: #8492A6;
  margin: 0 5px;
}
.project-extra {
  text-align: center;
  font-size: 13px;
  color: #8492A6;
}
.project-extra .extra-item {
  margin: 0 10px;
}
</style>
