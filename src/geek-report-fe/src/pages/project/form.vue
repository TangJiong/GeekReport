<template>
  <div class="main-container">
    <div class="container-body">
      <el-row>
        <el-col :span="16" :offset="4">
          <div class="form-container">
            <el-form label-position="right" ref="form" :model="project" label-width="120px">
              <el-form-item label="项目名称">
                <el-input v-model="project.title"></el-input>
              </el-form-item>
              <el-form-item label="数据源配置">
                <el-row>
                  <el-button>新增数据源</el-button>
                </el-row>
                <div class="divider"></div>
                <el-form label-position="left" label-width="120px" :model="datasource">
                  <el-form-item label="数据源名称">
                    <el-input v-model="datasource.title"></el-input>
                  </el-form-item>
                  <el-form-item label="驱动路径">
                    <el-input v-model="datasource.driver"></el-input>
                  </el-form-item>
                  <el-form-item label="驱动配置">
                    <el-input v-model="datasource.config"></el-input>
                  </el-form-item>
                </el-form>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit">立即创建</el-button>
                <el-button @click="onCancel">取消</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        project: {
          title: ''
        },
        datasource: {
          title: '',
          driver: '',
          config: ''
        }
      }
    },
    methods: {
      onSubmit () {
        console.log('submit!')
      },

      onCancel () {
        this.$router.push({name: 'project-list'})
      }
    }
  }
</script>

<style scoped>
.form-container {
  margin: 10px 0;
}
</style>
