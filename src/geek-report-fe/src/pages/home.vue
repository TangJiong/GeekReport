<template>
  <div>
    <el-menu theme="dark" :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1">
        <router-link :to="{name: 'home', params: {username: user.username}}">GeekReport</router-link>
      </el-menu-item>
      <el-menu-item index="2">
        <router-link :to="{name: 'project', params: {username: user.username}}">我的项目</router-link>
      </el-menu-item>
      <el-menu-item index="3">
        <router-link :to="{name: 'profile'}">个人中心</router-link>
      </el-menu-item>
      <el-menu-item index="4">
        <router-link :to="{name: 'setting'}">系统设置</router-link>
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
