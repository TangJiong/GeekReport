<template>
  <el-card class="project-card" :body-style="{padding: '0px'}">
    <div class="project-wrapper">
      <img class="project-image" :src="'static/charts' + ((project.id+1) % 3 + 1) + '.png'">
      <div class="project-description">
        <div class="title">
          <router-link :to="{name: 'project-detail', params: {projectId: project.id}}">
            {{ project.title }}
          </router-link>
        </div>
        <div class="extra">{{ project.created_at | toReadableTime }}</div>
      </div>
    </div>
    <div class="project-menu">
      <router-link class="menu-item" :to="{name: 'project-detail', params: {projectId: project.id}}">
        <i class="fa fa-eye" aria-hidden="true"></i>
      </router-link>
      <router-link class="menu-item" :to="{name: 'project-edit', params: {projectId: project.id}}">
        <i class="fa fa-pencil" aria-hidden="true"></i>
      </router-link>
      <router-link class="menu-item" :to="{name: 'project-setting', params: {projectId: project.id}}">
        <i class="fa fa-cog" aria-hidden="true"></i>
      </router-link>
    </div>
  </el-card>
</template>
<script>
export default {
  props: {
    project: {
      type: Object,
      required: true
    }
  }
}
</script>
<style>
.project-card {
  flex: 0 0 auto;
  width: 180px;
  margin: 10px;
}

.project-wrapper {
  position: relative;
  width: 180px;
  height: 180px;
}

.project-wrapper .project-image{
  display: block;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: auto;
}

.project-description {
  padding: 0.3em;
  position: absolute;
  bottom: 0;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.3);
}

.project-description .title {
  color: #fff;
}

.project-description .title a{
  color: #fff;
}

.project-description .extra {
  font-size: 13px;
  color: #eee;
}

.project-menu {
  display: flex;
}

.project-menu .menu-item{
  flex: 1 0 auto;
  text-align: center;
  color: #324057;

  display: block;
  padding: 0.4em 1em;
}

.project-menu .menu-item:not(:last-child){
  border-right: #D3DCE6 solid 1px;
}
</style>
