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
            <el-button type="primary"><i class="fa fa-plus" aria-hidden="true"></i> 创建项目</el-button>
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
        <div class="card-wrapper">
          <el-card v-for="(o, index) in 8" :key="index" class="project-card" :body-style="{padding: '0px'}">
            <div class="project-wrapper">
              <img class="project-image" :src="'static/charts' + ((index+1) % 3 + 1) + '.png'">
              <div class="project-description">
                <div class="title">项目名称</div>
                <div class="extra">{{ currentDate }}</div>
              </div>
            </div>
            <div class="project-menu">
              <router-link class="menu-item" :to="{name: 'project-detail', params: {projectId: index}}">
                <i class="fa fa-eye" aria-hidden="true"></i>
              </router-link>
              <router-link class="menu-item" :to="{name: 'project-detail', params: {projectId: index}}">
                <i class="fa fa-pencil" aria-hidden="true"></i>
              </router-link>
              <router-link class="menu-item" :to="{name: 'project-setting', params: {projectId: index}}">
                <i class="fa fa-cog" aria-hidden="true"></i>
              </router-link>
            </div>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment'

export default {
  data () {
    return {
      currentDate: moment().format('YYYY-MMM-DD HH:mm'),
      search: ''
    }
  },

  methods: {
    doSearch () {
      console.log('searching project...')
    }
  }
}
</script>

<style scoped>
.card-wrapper {
  display: flex;
  flex-wrap: wrap;
}

.project-card {
  flex: 0 0 auto;
  width: 180px;
  margin: 10px;
}

.project-wrapper {
  position: relative;
  width: 180px;
  height: 180px;
}

.project-wrapper .project-image{
  display: block;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: auto;
}

.project-description {
  padding: 0.3em;
  position: absolute;
  bottom: 0;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.3);
}

.project-description .title {
  color: #fff;
}

.project-description .extra {
  font-size: 13px;
  color: #bbb;
}

.project-menu {
  display: flex;
}

.project-menu .menu-item{
  flex: 1 0 auto;
  text-align: center;
  color: #324057;

  display: block;
  padding: 0.4em 1em;
}

.project-menu .menu-item:not(:last-child){
  border-right: #D3DCE6 solid 1px;
}

</style>
