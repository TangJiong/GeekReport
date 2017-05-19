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

var ParagraphService = {
  create (paragraph) {
    return Vue.http.post('paragraph', paragraph).then(response => response.data)
  },

  update (paragraph) {
    return Vue.http.patch('paragraph', paragraph).then(response => response.data)
  },

  getByProject (projectId) {
    return Vue.http.get('paragraph?project_id=' + projectId).then(response => response.data)
  },

  getById (id) {
    return Vue.http.get('paragraph/' + id).then(response => response.data)
  },

  delete (id) {
    return Vue.http.delete('paragraph/' + id).then(response => response.data)
  }
}

var QueryService = {
  run (query) {
    return Vue.http.post('query/run', query).then(response => response.data)
  },

  save (query) {
    return Vue.http.post('query/save', query).then(response => response.data)
  },

  getByParagraph (paraId) {
    return Vue.http.get('query?paragraph_id=' + paraId).then(response => response.data)
  },

  delete (id) {
    return Vue.http.delete('query/' + id).then(response => response.data)
  },

  setMaxAge (config) {
    return Vue.http.patch('query/maxage', config).then(response => response.data)
  }
}

export {
  DatasourceService,
  ProjectService,
  ParagraphService,
  QueryService
}
