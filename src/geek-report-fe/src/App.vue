<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
html,body {
  margin: 0;
  padding: 0;
  height: 100%;
}

#app {
  margin: 0;
  padding: 0;
  height: 100%;
}

.app-body {
  height: 100%;
}

.main-container {
  height: calc(100% - 60px);
  display: flex;
}

.container-sidebar {
  flex: 0 0 180px;
  height: 100%;
  background: #eef1f6;
  overflow: auto;
}

.container-body {
  flex: 1 1 auto;
  height: 100%;
  overflow: auto;
}

a {
  text-decoration: none;
}

.small-padding-container {
  padding: 10px;
}

.divider {
  margin: 10px 0;
  border-bottom: #f5f5f5 solid 1px;
}
</style>
