<template>
  <div
    class="card-wrapper"
    v-loading="loading"
    element-loading-text="Loading...">
    <template v-if="type === 'card'">
      <project-card
        v-for="project in projects"
        :key="project.id"
        :project="project"></project-card>
    </template>
  </div>
</template>

<script>
import ProjectCard from './ProjectCard'
export default {
  name: 'project-list',

  components: {
    ProjectCard: ProjectCard
  },

  props: {
    data: {
      type: Array | Promise,
      required: true
    },
    type: {
      type: String,
      default: 'card'
    }
  },

  data () {
    return {
      loading: true,
      projects: []
    }
  },

  created () {
    this.init()
  },

  methods: {
    init () {
      let vm = this
      if (this.data && this.data.then) {
        this.data.then(data => {
          vm.projects = data
          vm.loading = false
        })
      } else {
        this.projects = this.data || []
      }
    }
  }
}
</script>

<style scoped>
.card-wrapper {
  display: flex;
  flex-wrap: wrap;
  min-height: 200px;
}
</style>
