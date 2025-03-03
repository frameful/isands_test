<script setup lang="ts">

import { NuxtLayout } from "#components";

</script>

<template>
  <div class="bg-background h-screen">
    <NuxtLayout>
      <NuxtPage/>
      <Toaster />
    </NuxtLayout>
  </div>
</template>
