<template>
  <div class="small-padding-container">
    <div v-if="type === 'edit'" class="new-para-container">
      <el-form :inline="true" :model="newParagraph">
        <el-form-item label="标题">
          <el-input v-model="newParagraph.title" placeholder="点此输入标题"></el-input>
        </el-form-item>
          <el-button type="primary" @click="handleNewParagraph">新建段落</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="paragraph-grid">
      <paragraph
        v-for="paragraph in paragraphs"
        :key="paragraph.id"
        :editable="type === 'edit'"
        :paragraph="paragraph"></paragraph>
    </div>
  </div>
</template>

<script>
import Paragraph from '../paragraph/view'
import {
  ParagraphService
} from '@/services'

export default {
  components: {
    Paragraph
  },

  props: {
    type: {
      type: String,
      default: 'view'
    }
  },

  data () {
    return {
      newParagraph: {
        title: ''
      },
      paragraphs: []
    }
  },

  created () {
    this.init()
  },

  methods: {
    init () {
      this.initParagraphs()
    },

    initParagraphs () {
      let projectId = this.$route.params.projectId
      let vm = this
      ParagraphService.getByProject(projectId).then(({data}) => {
        vm.paragraphs = data
      })
    },

    handleNewParagraph () {
      let paragraph = {
        title: this.newParagraph.title,
        project_id: this.$route.params.projectId
      }
      let vm = this
      ParagraphService.create(paragraph).then(({id}) => {
        this.$router.push({name: 'paragraph-edit', params: {pId: id}})
      }).catch(({status, statusText}) => {
        vm.$message.error(status + ' ' + statusText)
      })
    }

  },

  watch: {
    '$route': 'init'
  }
}
</script>

<style scoped>
.new-para-container{
  border: 1px solid #dfe6ec;
  padding: 10px;
  margin: 10px 0;
  text-align: center;
}
.new-para-container .el-form-item{
  margin-bottom: 0;
}
.new-para-container .el-input{
  width: 300px;
}
.paragraph-grid {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-between;
}
</style>
