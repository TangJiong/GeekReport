import Vue from 'vue'

// function onError (response) {
//   return {
//     status: response.status,
//     message: response.statusText
//   }
// }

var DatasourceService = {
  create (datasource) {
    return Vue.http.post('datasource', datasource).then(response => response.data)
  },

  update (datasource) {
    return Vue.http.patch('datasource', datasource).then(response => response.data)
  },

  getByProject (projectId) {
    return Vue.http.get('datasource?project_id=' + projectId).then(response => response.data)
  },

  getById (id) {
    return Vue.http.get('datasource/' + id).then(response => response.data)
  },

  delete (id) {
    return Vue.http.delete('datasource/' + id).then(response => response.data)
  }
}

var ProjectService = {
  create (project) {
    return Vue.http.post('project', project).then(response => response.data)
  },

  update (project) {
    return Vue.http.patch('project', project).then(response => response.data)
  },

  getByUser (userId) {
    return Vue.http.get('project?user_id=' + userId).then(response => response.data)
  },

  getById (id) {
    return Vue.http.get('project/' + id).then(response => response.data)
  },

  delete (id) {
    return Vue.http.delete('project/' + id).then(response => response.data)
  }
}

export {
  DatasourceService,
  ProjectService
}
