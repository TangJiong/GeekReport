<template>
  <div class="paragraph-wrapper" :style="sizeStyle">
    <div class="title-container">
      <div class="paragraph-title">{{ paragraph.title }}</div>
      <div class="paragraph-menu">
        <el-button-group>
          <el-button size="small" @click="handleRefresh"><i class="fa fa-refresh" aria-hidden="true"></i></el-button>
          <el-button v-if="editable" size="small" @click="handleEdit"><i class="fa fa-pencil" aria-hidden="true"></i></el-button>
          <el-button size="small" @click="handleMoreInfo"><i class="fa fa-info" aria-hidden="true"></i></el-button>
        </el-button-group>
      </div>
    </div>
    <div class="output-container">
      <div class="output-wrapper">
        <div class="loading"
          v-if="!ready"
          v-loading="!ready"
          element-loading-text="拼命加载中..."></div>
        <visualization
          v-if="ready"
          :data="queryResult"
          :config="visualizationConfig"></visualization>
      </div>
    </div>
  </div>
</template>

<script>
import Visualization from '@/components/Visualization'
import {
  QueryService,
  VisualizationService
} from '@/services'

export default {
  components: {
    Visualization
  },

  props: {
    paragraph: {
      type: Object,
      required: true,
      default: () => {
        return {
          id: 0,
          title: '',
          width: 24,
          height: 1,
          default_visual_id: -1
        }
      }
    },
    editable: {
      type: Boolean,
      default: false
    }
  },

  data () {
    return {
      query: {},
      visualizationConfig: {},
      queryResult: {
        columns: [],
        rows: []
      },
      queryReady: false,
      visualConfigReady: false,
      queryResultReady: false
    }
  },

  computed: {
    sizeStyle () {
      let base = 4.1
      let width = this.paragraph.width
      let realWidth = (width === 24 ? 100 : width * base)
      return {
        flex: '0 0 ' + realWidth + '%'
      }
    },

    ready () {
      return this.queryReady && this.visualConfigReady && this.queryResultReady
    }
  },

  created () {
    this.init()
  },

  methods: {
    init () {
      this.fetchQuery()
      this.fetchVisualizationConfig()
    },

    handleRefresh () {
      this.runQuery()
    },

    handleEdit () {
      this.$router.push({name: 'paragraph-edit', params: {pId: this.paragraph.id}})
    },

    handleMoreInfo () {

    },

    fetchQuery () {
      QueryService.getByParagraph(this.paragraph.id).then(({data}) => {
        this.query = data
        this.queryReady = true
      })
    },

    fetchVisualizationConfig () {
      VisualizationService.getById(this.paragraph.default_visual_id).then(({data}) => {
        this.visualizationConfig = data
        this.visualConfigReady = true
      })
    },

    runQuery () {
      this.queryResultReady = false
      if (this.query !== null &&
        this.query.id !== undefined &&
        this.visualizationConfig !== null &&
        this.visualizationConfig.id !== undefined) {
        let query = {
          paragraph_id: this.query.paragraph_id,
          datasource_id: this.query.datasource_id,
          lang: this.query.lang,
          raw: this.query.raw
        }
        QueryService.run(query).then(({data}) => {
          this.queryResult = data
          this.queryResultReady = true
        })
      }
    }

  },

  watch: {
    'query': 'runQuery',
    'visualizationConfig': 'runQuery'
  }
}
</script>

<style scoped>
.paragraph-wrapper {
  border: 1px solid #dfe6ec;
  margin: 10px 0;
}
.title-container {
  display: flex;
  flex-wrap: wrap;
  border-bottom: 1px solid #dfe6ec;
  padding: 5px 10px;
  justify-content: space-between;
  align-items: center;
}
.paragraph-title {

}
.output-container {
  padding: 5px;
}
.output-wrapper {
  padding: 5px 0;
  color: #999;
  font-size: 14px;
}
.loading {
  height: 360px;
}
</style>
