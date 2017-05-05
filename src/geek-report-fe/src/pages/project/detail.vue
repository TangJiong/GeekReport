<template>
  <div class="main-container">
    <!-- <div class="container-sidebar">
      <el-menu default-active="1-1" :router="true">
        <el-submenu index="1">
          <template slot="title"><i class="el-icon-message"></i>主题1</template>
          <el-menu-item-group>
            <el-menu-item index="1-1" :route="{name: 'page-detail', params: {pageId: 1}}">
              炫酷报表1
            </el-menu-item>
            <el-menu-item index="1-2" :route="{name: 'page-detail', params: {pageId: 2}}">
              炫酷报表2
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-submenu index="2">
          <template slot="title"><i class="el-icon-message"></i>主题2</template>
          <el-menu-item-group>
            <el-menu-item index="2-1">页1</el-menu-item>
            <el-menu-item index="2-2">页2</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-submenu index="3">
          <template slot="title"><i class="el-icon-message"></i>主题3</template>
          <el-menu-item-group>
            <el-menu-item index="3-1">页1</el-menu-item>
            <el-menu-item index="3-2">页2</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>
    </div> -->
    <div class="container-body">
      <div class="project-title">{{ project.title }}</div>
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
  font-size: 24px;
  margin: 10px 5px;
}
<style>
