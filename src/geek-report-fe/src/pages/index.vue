<template>
  <div>
    <h1>GeekReport - Create Report in more GEEK way!</h1>
    <el-form label-position="right" label-width="80px" :model="formLabelAlign">
      <el-form-item label="账号">
        <el-input v-model="account"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="password"></el-input>
      </el-form-item>
      <el-button type="primary" @click="doLogin">登录</el-button>
    </el-form>
  </div>
</template>

<script>
export default {
  data () {
    return {
      account: '',
      password: ''
    }
  },

  methods: {
    doLogin () {
      this.$router.push({ name: 'home', params: { username: 'tangjiong' } })
    }
  }
}
</script>

<style scoped>

</style>
