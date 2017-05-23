<template>
  <div class="index-container">
    <h1 class="project title">GeekReport</h1>
    <h3 class="project subtitle">Create Report in more GEEK way!</h3>
    <el-row type="flex" justify="center">
      <el-col :span="8">
        <el-tabs v-model="activeName" type="card">
          <el-tab-pane label="已有账号" name="login">
            <el-form label-position="right" label-width="80px" >
              <el-form-item label="邮箱">
                <el-input v-model="user.email"></el-input>
              </el-form-item>
              <el-form-item label="密码">
                <el-input v-model="user.password"></el-input>
              </el-form-item>
              <el-form-item>
                <el-alert v-if="loginStatus !== ''" :title="loginStatus" type="error"></el-alert>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="doLogin">登录</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="创建账号" name="register">
            <el-form label-position="right" label-width="80px" >
              <el-form-item label="邮箱">
                <el-input v-model="user.email"></el-input>
              </el-form-item>
              <el-form-item label="用户名">
                <el-input v-model="user.name"></el-input>
              </el-form-item>
              <el-form-item label="密码">
                <el-input v-model="user.password"></el-input>
              </el-form-item>
              <el-form-item>
                <el-alert v-if="registerStatus !== ''" :title="registerStatus" type="error"></el-alert>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="doRegister">注册</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>

      </el-col>
    </el-row>
  </div>
</template>

<script>
import _ from 'lodash'
import {
  UserService
} from '@/services'

export default {
  data () {
    return {
      activeName: 'login',
      user: {
        email: '',
        name: '',
        password: ''
      },
      loginStatus: '',
      registerStatus: ''
    }
  },

  methods: {
    doLogin () {
      let user = _.pick(this.user, ['email', 'password'])
      UserService.login(user).then(({status, message, data}) => {
        if (status !== 200) {
          this.loginStatus = message
        } else {
          this.loginStatus = ''
          this.$store.commit('SET_USER', data)
          this.$router.push({ name: 'home', params: { username: data.name } })
        }
      }).catch(({status, statusText, error}) => {
        this.loginStatus = JSON.stringify(error)
      })
    },

    doRegister () {
      let user = _.pick(this.user, ['email', 'name', 'password'])
      UserService.register(user).then(({status, message, data}) => {
        if (status !== 200) {
          this.registerStatus = message
        } else {
          this.registerStatus = ''
          this.$message.success('注册成功，自动登录中')
          this.doLogin()
        }
      }).catch(({status, statusText, error}) => {
        this.registerStatus = JSON.stringify(error)
      })
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
