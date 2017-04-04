<template>
  <div class="index-container">
    <h1 class="project title">GeekReport</h1>
    <h3 class="project subtitle">Create Report in more GEEK way!</h3>
    <el-row type="flex" justify="center">
      <el-col :span="8">
        <el-form label-position="right" label-width="80px" >
          <el-form-item label="账号">
            <el-input v-model="account"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="doLogin">登录</el-button>
            <el-button type="text">创建账号</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
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
.index-container {
  width: 100%;
  height: 100%;
  background: url('../assets/background.png') no-repeat;
  background-size: cover;
}
.project.title {
  text-align: center;
  margin: 0;
  padding: 50px 0 10px 0;
}
.project.subtitle {
  text-align: center;
  margin: 0;
  padding: 10px 0 50px 0;
}
</style>
