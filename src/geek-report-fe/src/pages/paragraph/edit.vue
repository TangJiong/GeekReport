<template>
  <div class="small-padding-container">
    <div class="paragraph-container">
      <div class="paragraph-title">
        <span class="title-text">{{ paragraph.title }}</span>
        <el-button-group>
          <el-button type="default" @click="handleGoBack">
            <i class="fa fa-arrow-left" aria-hidden="true"></i>
            返回
          </el-button>
          <el-button type="primary" @click="dialogPConfigVisible = true">
            <i class="fa fa-upload" aria-hidden="true"></i>
            保存/发布
          </el-button>
          <el-button type="danger" @click="handleDeletePara">
            <i class="fa fa-trash" aria-hidden="true"></i>
            删除
          </el-button>
        </el-button-group>
      </div>
      <el-dialog
        title="段落信息"
        :value="dialogPConfigVisible"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        :show-close="false">
        <el-form :model="paragraph">
          <el-form-item label="段落标题" :label-width="formLabelWidth">
            <el-input v-model="paragraph.title" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="段落宽度" :label-width="formLabelWidth">
            <el-slider
              v-model="paragraph.width"
              :step="1"
              :min="1"
              :max="24"
              show-input>
            </el-slider>
          </el-form-item>
          <el-form-item label="默认可视化" :label-width="formLabelWidth">
            <el-select v-model="paragraph.default_visual_id" placeholder="请选择">
              <el-option
                v-for="item in visualizationList"
                :label="item.title + '-' + item.chart_type"
                :value="item.id"
                :key="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogPConfigVisible = false">取消</el-button>
          <el-button type="primary" @click="handleUpdateParagraph">确定发布</el-button>
        </div>
      </el-dialog>
      <div class="input-container">
        <div class="schema-container">
          <div class="container-menu">
            <div class="container-menu-item">
              <span class="item-label">数据源</span>
              <el-select class="container-menu-control" v-model="query.datasource_id" placeholder="请选择">
                <el-option
                  v-for="item in datasourceOptions"
                  :label="item.label"
                  :value="item.value"
                  :key="item.value">
                </el-option>
              </el-select>
            </div>
          </div>
          <ul class="schema-list">
            <li class="schema-item">
              <i class="fa fa-table" aria-hidden="true"></i>
              t_user
            </li>
            <li class="schema-item">
              <i class="fa fa-table" aria-hidden="true"></i>
              t_sales
            </li>
            <li class="schema-item">
              <i class="fa fa-table" aria-hidden="true"></i>
              t_district
            </li>
            <li class="schema-item">
              <i class="fa fa-table" aria-hidden="true"></i>
              t_product
            </li>
            <li class="schema-item">
              <i class="fa fa-table" aria-hidden="true"></i>
              t_store
            </li>
          </ul>
        </div>
        <div class="editor-container">
          <div class="container-menu">
            <div class="container-menu-item">
              <span class="item-label">语言</span>
              <el-select v-model="query.lang" placeholder="请选择">
                <el-option
                  v-for="item in langs"
                  :label="item.label"
                  :value="item.value"
                  :key="item.value">
                </el-option>
              </el-select>
            </div>
            <div class="container-menu-item">
              <el-button-group>
                <el-button type="primary" @click="handleRunQuery">
                  <i class="fa fa-play" aria-hidden="true"></i> 执行
                </el-button>
                <!-- <el-button type="default">
                  <i class="fa fa-indent" aria-hidden="true"></i> 格式化
                </el-button> -->
                <el-button type="default" @click="handleSaveQuery">
                  <i class="fa fa-save" aria-hidden="true"></i> 保存
                </el-button>
                <el-button type="default" @click="handleConfigQuery">
                  <i class="fa fa-cog" aria-hidden="true"></i> 设置
                </el-button>
              </el-button-group>
            </div>
            <el-dialog
              title="查询设置"
              :value="dialogQConfigVisible"
              :close-on-click-modal="false"
              :close-on-press-escape="false"
              :show-close="false">
              <el-form :model="query">
                <el-form-item label="缓存时间(s)" :label-width="formLabelWidth">
                  <el-input-number v-model="query.max_age"></el-input-number>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogQConfigVisible = false">取消</el-button>
                <el-button type="primary" @click="handleUpdateQuery">更新</el-button>
              </div>
            </el-dialog>
          </div>
          <editor
            :default-value="codeDefault"
            :mode="query.lang"
            @code-change="handleCodeChange">
          </editor>
        </div>
      </div>
      <div class="output-container">
        <div class="output-container-title">
          <el-alert
            :title="queryStatusText"
            :description="queryStatusDetail"
            :type="queryStatus"
            :closable="false"
            show-icon>
          </el-alert>
        </div>
        <div class="output-wrapper" v-if="queryStatus === 'success'">
          <el-tabs v-model="activeName" @tab-click="handleTabClick">
            <el-tab-pane label="查询结果" name="first">
              <visual-table
                :data="queryResult"></visual-table>
            </el-tab-pane>
            <el-tab-pane
              v-for="visualItem in visualizationList"
              :key="visualItem.id"
              :label="visualItem.title + '-' + visualItem.chart_type"
              :name="'visual-' + visualItem.id">
              <visualization
                :data="queryResult"
                :config="visualItem"></visualization>
            </el-tab-pane>
            <el-tab-pane label="更多可视化类型" name="third">
              <div class="visual-container">
                <div class="visual-config">
                  <el-form :model="visualization">
                    <el-form-item label="名称" :label-width="formLabelWidth">
                      <el-input v-model="visualization.title"></el-input>
                    </el-form-item>
                    <el-form-item label="类型" :label-width="formLabelWidth">
                      <el-select v-model="visualization.chart_type" placeholder="请选择">
                        <el-option
                          v-for="item in chartTypes"
                          :label="item.label"
                          :value="item.value"
                          :key="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="分组列" :label-width="formLabelWidth">
                      <el-select v-model="visualization.group_column" placeholder="请选择">
                        <el-option
                          v-for="item in visualColumns"
                          :label="item.label"
                          :value="item.value"
                          :key="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="自变量列" :label-width="formLabelWidth">
                      <el-select v-model="visualization.observe_column" placeholder="请选择">
                        <el-option
                          v-for="item in visualColumns"
                          :label="item.label"
                          :value="item.value"
                          :key="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="因变量列" :label-width="formLabelWidth">
                      <el-select v-model="visualization.measure_column" placeholder="请选择">
                        <el-option
                          v-for="item in visualColumns"
                          :label="item.label"
                          :value="item.value"
                          :key="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>
                    <!-- <el-form-item label="设为默认" :label-width="formLabelWidth">
                      <el-switch
                        on-text="是"
                        off-text="否"
                        @change="handleDefaultVisualChange">
                      </el-switch>
                    </el-form-item> -->
                    <el-form-item :label-width="formLabelWidth">
                      <el-button type="primary" @click="handleSaveVisualization">保存</el-button>
                    </el-form-item>
                  </el-form>
                </div>
                <div class="visual-pre">
                  <visualization
                    :data="queryResult"
                    :config="visualization"></visualization>
                </div>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  ParagraphService,
  DatasourceService,
  QueryService,
  VisualizationService
} from '@/services'
import _ from 'lodash'
import Visualization from '@/components/Visualization'
import VisualTable from '@/components/VisualTable'
import VisualChart from '@/components/VisualChart'

export default {
  components: {
    Visualization,
    VisualTable,
    VisualChart
  },

  data () {
    return {
      paragraph: {
        title: ''
      },
      dialogPConfigVisible: false,
      formLabelWidth: '120px',
      datasourceOptions: [],
      langs: [
        {
          value: 'sql',
          label: 'SQL'
        }
      ],
      query: {
        paragraph_id: -1,
        datasource_id: -1,
        lang: 'sql',
        raw: '',
        max_age: 0
      },
      codeDefault: '',
      queryStatus: 'info',
      queryStatusText: '点击执行按钮，以查看结果',
      queryStatusDetail: '',
      queryResult: {
        rows: [],
        columns: []
      },
      dialogQConfigVisible: false,

      chartTypes: [
        {
          value: 'table',
          label: '表格'
        },
        {
          value: 'line',
          label: '折线图（Line Chart）'
        },
        {
          value: 'bar',
          label: '柱状图（Bar Chart）'
        },
        {
          value: 'pie',
          label: '饼图（Pie Chart）'
        },
        {
          value: 'bubble',
          label: '气泡图（Bubble Chart）'
        },
        {
          value: 'radar',
          label: '雷达图（Radar Chart）'
        },
        {
          value: 'polarArea',
          label: 'Polar Area Chart'
        }
      ],
      visualizationList: [],
      visualization: {
        title: '',
        chart_type: 'table',
        group_column: '',
        observe_column: '',
        measure_column: ''
      },
      activeName: 'first'
    }
  },

  computed: {
    visualColumns () {
      return this.queryResult.columns.map(column => {
        return {label: column, value: column}
      })
    },

    preChartConfig () {
      return VisualizationService.build(this.queryResult.rows, this.visualization)
    }
  },

  created () {
    this.init()
  },

  methods: {
    init () {
      this.initParagraph()
      this.fetchDatasourceOptions()
      this.initQuery()
    },

    initParagraph () {
      let paragraphId = this.$route.params.pId
      let vm = this
      ParagraphService.getById(paragraphId).then(({data}) => {
        vm.paragraph = data
      }).catch(({status, statusText}) => {
        vm.$message.error(status + ' ' + statusText)
      })
    },

    fetchDatasourceOptions () {
      let projectId = this.$route.params.projectId
      let vm = this
      DatasourceService.getByProject(projectId).then(({data}) => {
        vm.datasourceOptions = data.map(item => {
          return {value: item.id, label: item.name}
        })
      }).catch(({status, statusText}) => {
        vm.$message.error(status + ' ' + statusText)
      })
    },

    initQuery () {
      let paragraphId = this.$route.params.pId
      let vm = this
      QueryService.getByParagraph(paragraphId).then(({data}) => {
        if (data !== null) { // paragraph has saved query
          vm.query = data
          vm.codeDefault = data.raw
        }
      }).catch(({status, statusText}) => {
        vm.$message.error(status + ' ' + statusText)
      })
    },

    handleUpdateParagraph () {
      let paragraph = _.omit(this.paragraph, ['project_id', 'created_at'])
      let vm = this
      ParagraphService.update(paragraph).then(() => {
        vm.$message.success('更新成功！')
        vm.dialogPConfigVisible = false
        vm.handleGoBack()
      }).catch(({status, statusText}) => {
        vm.$message.error(status + ' ' + statusText)
      })
    },

    handleGoBack () {
      this.$router.push({name: 'project-detail', params: {projectId: this.$route.params.projectId}})
    },

    handleDeletePara () {
      this.$confirm('删除操作不可撤销, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.doDeletePara()
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },

    doDeletePara () {
      if (this.paragraph.id !== undefined) {
        ParagraphService.delete(this.paragraph.id).then(({status}) => {
          this.$message.success('删除成功!')
        }).catch(({status, statusText, error}) => {
          this.$message.error(status + ' ' + statusText)
        })
      }
    },

    handleCodeChange (val) {
      this.query.raw = val
    },

    handleRunQuery () {
      if (this.query.raw !== '') {
        this.queryStatus = 'info'
        this.queryStatusText = '查询执行中...'
        let query = {
          paragraph_id: parseInt(this.$route.params.pId),
          datasource_id: this.query.datasource_id,
          lang: this.query.lang,
          raw: this.query.raw
        }
        QueryService.run(query).then(({data}) => {
          this.queryStatus = 'success'
          this.queryStatusText = '查询成功'
          this.queryResult = data
        }).catch(({status, statusText, data}) => {
          this.queryStatus = 'error'
          this.queryStatusText = status + '' + statusText
          this.queryStatusDetail = JSON.stringify(data)
        })
      } else {
        this.queryStatus = 'warning'
        this.queryStatusText = '请输入查询语句'
      }
    },

    handleSaveQuery () {
      let query = this.query
      if (query.id !== undefined && query.id !== -1) { // already exists, save to update
        query = _.omit(query, ['created_at'])
      } else { // new, save to create
        query.id = -1
      }
      query.paragraph_id = this.paragraph.id
      QueryService.save(query).then(({id}) => {
        if (id !== null) {
          this.query.id = id
        }
        this.$message.success('保存成功')
      }).catch(({status, statusText}) => {
        this.query.id = -1
        this.$message.error(status + '' + statusText)
      })
    },

    handleConfigQuery () {
      this.dialogQConfigVisible = true
    },

    handleUpdateQuery () {
      if (this.query.id !== undefined && this.query.id !== -1) {
        QueryService.setMaxAge({
          id: this.query.id,
          max_age: this.query.max_age
        }).then((resp) => {
          this.dialogQConfigVisible = false
          this.$message.success('设置成功')
        }).catch(({status, statusText}) => {
          this.$message.error(status + '' + statusText)
        })
      }
    },

    initVisualizationList () {
      if (this.query.id !== undefined && this.query.id !== -1) {
        VisualizationService.getByQuery(this.query.id).then(({data}) => {
          this.visualizationList = data
        })
      }
    },

    handleSaveVisualization () {
      if (this.query.id !== undefined && this.query.id !== -1) {
        let visual = _.cloneDeep(this.visualization)
        visual.query_id = this.query.id
        VisualizationService.create(visual).then(({id}) => {
          this.$message.success('保存成功')
          this.initVisualizationList()
        }).catch(({status, statusText}) => {
          this.$message.error(status + ' ' + statusText)
        })
      } else {
        this.$alert('查询已修改，请先保存查询！', '警告', {
          confirmButtonText: '确定'
        })
      }
    },

    handleTabClick (tab, event) {
      console.log(tab, event)
    }
  },

  watch: {
    '$route': 'init',
    'query': 'initVisualizationList'
  }
}
</script>

<style scoped>
.paragraph-container {
  border: 1px solid #dfe6ec;
  margin-bottom: 40px;
}
.paragraph-title {
  padding: 10px 5px;
  border-bottom: 1px solid #dfe6ec;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.title-text {
  font-size: 16px;
}
.input-container {
  display: flex;
  justify-content: space-between;
}
.schema-container {
  height: 405px;
  flex: 0 0 260px;
  border-right: 1px solid #dfe6ec;
  overflow: auto;
}
.schema-container-title {
  height: 38px;
  color: #999;
  border-bottom: 1px solid #dfe6ec;
}
.schema-list {
  list-style: none;
  -webkit-margin-before: 0.3em;
  -webkit-margin-after: 0.3em;
  -webkit-margin-start: 0px;
  -webkit-margin-end: 0px;
  -webkit-padding-start: 10px;
}
.schema-item {
  color: #1f2d3d;
}
.editor-container {
  height: 405px;
  flex: 1 1 auto;
}
.container-menu {
  height: 44px;
  border-bottom: 1px solid #dfe6ec;
  display: flex;
  align-items: center;
}
.container-menu-item {
  display: flex;
  margin: 5px;
  align-items: center;
}
.container-menu-item .item-label {
  flex: 1 1 auto;
  margin-right: 5px;
  font-size: 14px;
  font-weight: normal;
  color: #1f2d3d;
}
.container-menu-item .item-control {
  flex: 1 1 auto;
}
.output-container {
  padding: 5px;
  border-top: 1px solid #dfe6ec;
}
.output-container-title {
  color: #999;
  font-size: 14px;
  padding-bottom: 5px;
  border-bottom: 1px solid #dfe6ec;
}
.output-wrapper {
  padding: 5px 0;
  color: #999;
  font-size: 14px;
}
.visual-container {
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
}
.visual-config {
  flex: 0 0 49%;
  border-right: 1px solid #dfe6ec;
  padding: 5px;
}
.visual-pre {
  flex: 1 1 auto;
  padding: 5px;
}
</style>
