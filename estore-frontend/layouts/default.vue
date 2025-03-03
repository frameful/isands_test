<script lang="ts">
import { defineComponent } from 'vue'
import DarkModeToggle from "~/components/ui/darkmode-toggle/DarkModeToggle.vue";

export default defineComponent({
  name: "default.vue",
  components: {DarkModeToggle}
})
</script>

<template>
  <div>
    <div class="flex justify-end mx-12 mt-2 container">
      <DarkModeToggle />
    </div>
    <slot />
  </div>
</template>

<style scoped>

</style>