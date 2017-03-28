<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/username' }">控制台</el-breadcrumb-item>
      <el-breadcrumb-item>项目1</el-breadcrumb-item>
      <el-breadcrumb-item>主题1</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row>
      <el-col :span="4">
        <el-menu default-active="1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose">
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-message"></i>主题1</template>
            <el-menu-item-group>
              <el-menu-item index="1-1">页1</el-menu-item>
              <el-menu-item index="1-2">页2</el-menu-item>
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
      </el-col>
      <el-col :span="20">
        <router-view></router-view>
      </el-col>
    </el-row>
  </div>
</template>
