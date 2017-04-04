<template>
  <div class="main-container">
    <div class="container-sidebar">
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
    </div>
    <div class="container-body">
      <el-breadcrumb style="margin: 10px;" separator="/">
        <el-breadcrumb-item :to="{ name: 'project' }">项目</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ name: 'project' }">主题1</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ name: 'project' }">报表1</el-breadcrumb-item>
      </el-breadcrumb>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {}
  }
}
</script>

<style>
.small-margin {
  margin: 5px;
}
.breadcrumb-container {
  padding: 5px;
}
<style>
