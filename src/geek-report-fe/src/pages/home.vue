<template>
  <div class="app-body">
    <el-menu theme="dark" :router="true" :default-active="activeIndex" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1" :route="{name: 'home', params: {username: user.username}}">
        GeekReport
      </el-menu-item>
      <el-menu-item index="2" :route="{name: 'project', params: {username: user.username}}">
        项目
      </el-menu-item>
      <el-menu-item index="3" :route="{name: 'profile'}">
        个人中心
      </el-menu-item>
      <el-menu-item index="4" :route="{name: 'setting'}">
        系统设置
      </el-menu-item>
    </el-menu>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  data () {
    return {
      activeIndex: '1',
      user: {
        username: 'tangjiong'
      }
    }
  },
  methods: {
    handleSelect (key, keyPath) {
      console.log(key, keyPath)
    }
  }
}
</script>
<style>

</style>
