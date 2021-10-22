<template>
  <div id="app">
    <dashboard></dashboard>
  </div>
</template>

<script>
import dashboard from '@/views/dashboard'
export default {
  components:{
    dashboard,
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
