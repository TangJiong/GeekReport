<template>
  <div class="main-container">
    <div class="container-body">
      <div class="form-container">
        <el-form label-position="right" ref="form" :model="project" label-width="120px">
          <el-form-item label="项目名称">
            <el-input v-model="project.title"></el-input>
          </el-form-item>
          <el-form-item label="数据源配置">
            <el-table
              :data="datasources">
              <el-table-column type="expand">
                <template scope="props">
                  <el-form :label-width="labelWidth">
                    <el-form-item label="数据源名称">
                      <el-input v-model="props.row.name"></el-input>
                    </el-form-item>
                    <el-form-item label="数据库名称">
                      <!-- <el-input v-model="props.row.dbname"></el-input> -->
                      <el-select v-model="props.row.dbname" placeholder="请选择">
                        <el-option label="mysql" value="mysql"></el-option>
                        <el-option label="postgres" value="postgres"></el-option>
                      </el-select>
                    </el-form-item>
                    <!-- <el-form-item label="驱动路径" :label-width="labelWidth">
                      <el-input v-model="props.row.driver_path"></el-input>
                    </el-form-item> -->
                    <el-form-item label="驱动配置">
                      <el-input v-model="props.row.config"></el-input>
                    </el-form-item>
                    <el-form-item>
                      <el-button type="primary" @click="handleUpdateDatasource(props.row)">更新</el-button>
                    </el-form-item>
                    <!-- <el-row :gutter="20">
                      <el-col :span="12">

                      </el-col>
                      <el-col :span="12">

                      </el-col>
                      <el-col :span="24">

                      </el-col>
                      <el-col :span="24">

                      </el-col>
                    </el-row> -->
                    <!-- <div class="divider"></div>
                    <el-row style="text-align:center">
                      <el-button type="primary" @click="handleUpdateDatasource(props.row)">更新</el-button>
                    </el-row> -->
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column
                label="数据源名称"
                prop="name">
              </el-table-column>
              <el-table-column
                label="数据库名称"
                prop="dbname">
              </el-table-column>
              <el-table-column
                label="操作">
                <template scope="scope">
                  <el-button
                    size="small"
                    type="danger"
                    @click="handleDeleteDatasource(scope.$index, scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="divider"></div>
            <el-form label-position="left" label-width="120px" :model="datasource">
              <el-form-item label="数据源名称">
                <el-input v-model="datasource.name"></el-input>
              </el-form-item>
              <el-form-item label="数据库名称">
                <el-select v-model="datasource.dbname" placeholder="请选择">
                  <el-option label="mysql" value="mysql"></el-option>
                  <el-option label="postgres" value="postgres"></el-option>
                </el-select>
                <!-- <el-input v-model="datasource.dbname"></el-input> -->
              </el-form-item>
              <!-- <el-col :span="24">
                <el-form-item label="驱动路径">
                  <el-input v-model="datasource.driver_path"></el-input>
                </el-form-item>
              </el-col> -->
              <el-form-item label="驱动配置">
                <el-input v-model="datasource.config"></el-input>
              </el-form-item>
              <!-- <el-row :gutter="20">
                <el-col :span="12">

                </el-col>
                <el-col :span="12">

                </el-col>

                <el-col :span="24">

                </el-col>
              </el-row> -->
            </el-form>
            <div class="divider"></div>
            <el-row style="text-align:center">
              <el-button type="primary" @click="handleCreateDatasource">新增数据源</el-button>
            </el-row>
            <div class="divider"></div>

          </el-form-item>
          <el-form-item style="text-align:right">
            <el-button v-if="formType === 'EDIT'" type="primary" @click="handleUpdateProject">保存</el-button>
            <el-button v-else type="primary" @click="handleCreateProject">立即创建</el-button>
            <el-button @click="handleCancel">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import _ from 'lodash'
import {
  ProjectService,
  DatasourceService
} from '@/services'

export default {
  data () {
    return {
      labelWidth: '120px',
      project: {
        title: ''
      },
      datasource: {
        name: '',
        dbname: '',
        driver_path: 'embedded',
        config: ''
      },
      datasources: []
    }
  },

  computed: {
    formType () {
      if (this.$route.params.projectId !== undefined) {
        return 'EDIT'
      } else {
        return 'ADD'
      }
    }
  },

  created () {
    this.initData()
  },

  methods: {
    initData () {
      let vm = this
      // projectId exists in $route.params => edit
      // else => add
      if (this.$route.params.projectId !== undefined) {
        let projectId = this.$route.params.projectId
        ProjectService.getById(projectId).then(({data}) => {
          vm.project = data
        })
        DatasourceService.getByProject(projectId).then(({data}) => {
          vm.datasources = data
        })
      }
    },

    go2ProjectList () {
      this.$router.push({name: 'project-list'})
    },

    handleCreateDatasource () {
      let datasource = _.cloneDeep(this.datasource)
      this.datasources.push(datasource)
      this.doCreateDatasource(datasource)
      this.datasource = {
        name: '',
        dbname: '',
        driver_path: '',
        config: ''
      }
    },

    handleUpdateDatasource (datasource) {
      this.doUpdateDatasource(datasource)
    },

    handleDeleteDatasource (index, row) {
      this.datasources = this.datasources.filter(datasource => {
        return !_.isEqual(datasource, row)
      })
      if (row.id !== undefined) {
        this.doDeleteDatasource(row.id)
      }
    },

    handleCreateProject () {
      let vm = this
      // TODO
      let userId = 1
      let project = _.cloneDeep(this.project)
      _.assign(project, {created_by: userId})
      let datasources = _.cloneDeep(this.datasources)
      ProjectService.create(project).then(({id}) => {
        datasources.forEach((datasource) => {
          _.assign(datasource, {project_id: id})
          DatasourceService.create(datasource)
        })
        vm.$message.success('新建项目成功！')
        vm.go2ProjectList()
      }).catch(({status, statusText}) => {
        vm.$message.error(status + ' ' + statusText)
      })
    },

    handleUpdateProject () {
      if (this.$route.params.projectId !== undefined) {
        let vm = this
        ProjectService.update({
          id: this.$route.params.projectId,
          title: this.project.title
        }).then(() => {
          vm.$message.success('更新项目成功！')
          vm.go2ProjectList()
        }).catch(({status, statusText}) => {
          vm.$message.error(status + ' ' + statusText)
        })
      }
    },

    handleCancel () {
      this.go2ProjectList()
    },

    doCreateDatasource (datasource) {
      if (this.$route.params.projectId !== undefined) {
        datasource.project_id = this.$route.params.projectId
        DatasourceService.create(datasource)
      }
    },

    doUpdateDatasource (datasource) {
      if (datasource.id !== undefined) {
        datasource = _.omit(datasource, ['project_id', 'created_at'])
        let vm = this
        DatasourceService.update(datasource).then(() => {
          vm.$message.success('更新成功！')
        }).catch(({status, statusText}) => {
          vm.$message.error(status + ' ' + statusText)
        })
      }
    },

    doDeleteDatasource (id) {
      DatasourceService.delete(id)
    }
  },

  watch: {
    '$route': 'initData'
  }
}
</script>

<style scoped>
.form-container {
  margin: 10px 15%;
}
</style>
