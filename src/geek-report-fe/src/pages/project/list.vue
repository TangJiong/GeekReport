<template>
  <div class="main-container">
    <div class="container-sidebar">
      <el-menu mode="vertical" default-active="1" class="el-menu-vertical">
        <el-menu-item-group title="项目">
          <el-menu-item index="1"><i class="el-icon-message"></i>我创建的</el-menu-item>
          <el-menu-item index="2"><i class="el-icon-message"></i>我收藏的</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="动态">
          <el-menu-item index="3"><i class="el-icon-message"></i>动态</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="快速选择">
          <el-menu-item index="4"><i class="el-icon-share"></i>报表名称</el-menu-item>
          <el-menu-item index="5"><i class="el-icon-share"></i>报表名称</el-menu-item>
        </el-menu-item-group>
      </el-menu>
    </div>
    <div class="container-body">
      <div class="small-padding-container">
        <el-row type="flex" justify="space-between">
          <el-col :span="4">
            <el-button type="primary" @click="newProject"><i class="fa fa-plus" aria-hidden="true"></i> 创建项目</el-button>
          </el-col>
          <el-col :span="10">
            <el-row type="flex" justify="space-around">
              <el-col :span="12">
                <el-input
                  placeholder="搜索"
                  icon="search"
                  v-model="search"
                  :on-icon-click="doSearch">
                  </el-input>
              </el-col>
              <el-col :span="10">
                <el-button-group>
                  <el-button type="default"><i class="fa fa-list" aria-hidden="true"></i></el-button>
                  <el-button type="default"><i class="fa fa-th-large" aria-hidden="true"></i></el-button>
                </el-button-group>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
        <div class="divider"></div>
        <project-list :data="projects"></project-list>
      </div>
    </div>
  </div>
</template>

<script>
// import moment from 'moment'
import ProjectList from '@/components/ProjectList'
import {ProjectService} from '@/services'

export default {
  components: {
    ProjectList
  },

  data () {
    return {
      search: '',
      projects: Promise.resolve([])
    }
  },

  created () {
    this.initProjects()
  },

  methods: {
    initProjects () {
      let userId = this.$store.state.user.id
      this.projects = ProjectService.getByUser(userId).then(({data}) => data)
    },

    doSearch () {
      console.log('searching project...')
    },

    newProject () {
      this.$router.push({name: 'project-new'})
    }

  }
}
</script>

<style scoped>

</style>
