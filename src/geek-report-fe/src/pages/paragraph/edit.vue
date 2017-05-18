<template>
  <div class="small-padding-container">
    <div class="paragraph-container">
      <div class="paragraph-title">
        {{ paragraph.title }}
        <el-button type="text" @click="dialogPConfigVisible = true">
          <i class="fa fa-cog" aria-hidden="true"></i>
          设置
        </el-button>
      </div>
      <el-dialog
        title="段落设置"
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
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogPConfigVisible = false">取消</el-button>
          <el-button type="primary" @click="handleUpdateParagraph">更新</el-button>
        </div>
      </el-dialog>
      <div class="input-container">
        <div class="schema-container">
          <div class="container-menu">
            <div class="container-menu-item">
              <span class="item-label">数据源</span>
              <el-select class="container-menu-control" v-model="value" placeholder="请选择">
                <el-option
                  v-for="item in options"
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
              <el-select v-model="lang" placeholder="请选择">
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
                <el-button type="primary">
                  <i class="fa fa-play" aria-hidden="true"></i> 执行
                </el-button>
                <el-button type="default">
                  <i class="fa fa-indent" aria-hidden="true"></i> 格式化
                </el-button>
                <el-button type="default">
                  <i class="fa fa-save" aria-hidden="true"></i> 保存
                </el-button>
                <el-button type="default">
                  <i class="fa fa-cog" aria-hidden="true"></i> 设置
                </el-button>
              </el-button-group>
            </div>
          </div>
          <editor
            :mode="lang">
          </editor>
        </div>
      </div>
      <div class="output-container">
        <div class="output-container-title">输出结果</div>
        <div class="output-wrapper">
          <el-tabs v-model="activeName" @tab-click="handleTabClick">
            <el-tab-pane label="表格" name="first">
              <el-table
                :data="tableData"
                height="300"
                border
                style="width: 100%">
                <el-table-column
                  prop="date"
                  label="日期"
                  width="180">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="姓名"
                  width="180">
                </el-table-column>
                <el-table-column
                  prop="address"
                  label="地址">
                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="Charts" name="second">chart's</el-tab-pane>
            <el-tab-pane label="更多" name="third">自定义可视化</el-tab-pane>
          </el-tabs>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  ParagraphService
} from '@/services'
import _ from 'lodash'

export default {
  data () {
    return {
      paragraph: {
        title: ''
      },
      dialogPConfigVisible: false,
      formLabelWidth: '120px',

      activeNames: ['1'],
      options: [
        {
          value: 'Spark',
          label: 'Spark'
        },
        {
          value: 'MySQL',
          label: 'MySQL'
        },
        {
          value: 'MongoDB',
          label: 'MongoDB'
        },
        {
          value: 'Cassandra',
          label: 'Cassandra'
        }
      ],
      value: 'MongoDB',
      langs: [
        {
          value: 'scala',
          label: 'Scala'
        },
        {
          value: 'javascript',
          label: 'JavaScript'
        },
        {
          value: 'sql',
          label: 'SQL'
        }
      ],
      lang: 'javascript',
      tableData: [
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          date: '2016-05-08',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          date: '2016-05-06',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          date: '2016-05-07',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }
      ],
      activeName: 'first'
    }
  },

  created () {
    this.init()
  },

  methods: {
    init () {
      this.initParagraph()
    },

    initParagraph () {
      let paragraphId = this.$route.params.pId
      let vm = this
      ParagraphService.getById(paragraphId).then(({data}) => {
        vm.paragraph = data
      })
    },

    handleUpdateParagraph () {
      let paragraph = _.omit(this.paragraph, ['project_id', 'created_at'])
      let vm = this
      ParagraphService.update(paragraph).then(() => {
        vm.$message.success('更新成功！')
        vm.dialogPConfigVisible = false
      }).catch(({status, statusText}) => {
        vm.$message.error(status + ' ' + statusText)
      })
    },

    handleTabClick (tab, event) {
      console.log(tab, event)
    }
  },

  watch: {
    '$route': 'init'
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
</style>
