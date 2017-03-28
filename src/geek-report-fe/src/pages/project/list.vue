<template>
  <div>
    <h2>项目-列表</h2>
    <el-row>
      <el-col :span="4" v-for="(o, index) in 4">
        <el-card :body-style="{margin: '5px', padding: '0px'}">
          <img src="../../assets/logo.png" class="image">
          <div style="padding: 14px;">
            <span>项目名称</span>
            <div class="bottom clearfix">
              <time class="time">{{ currentDate }}</time>
              <el-button type="text" class="button">
                <router-link :to="{name: 'project-detail', params: {projectId: index}}">进入</router-link>
              </el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data () {
    return {
      currentDate: new Date()
    }
  }
}
</script>

<style>
  .time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }

  .clearfix:after {
      clear: both
  }
</style>
