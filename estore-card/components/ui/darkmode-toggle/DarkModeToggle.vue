<script setup lang="ts">
import { Button } from "@/components/ui/button";

import { Icon } from "@iconify/vue";
import { Tooltip, TooltipProvider, TooltipContent, TooltipTrigger } from "@/components/ui/tooltip";

const colorMode = useColorMode();
</script>

<template>
  <TooltipProvider :delay-duration="200">
    <Tooltip>
      <TooltipTrigger as-child>
        <Button variant="ghost"
                @click="colorMode.preference == 'light' ? colorMode.preference = 'dark' : colorMode.preference = 'light'">
          <Icon
              icon="radix-icons:moon"
              class="h-[1.2rem] w-[1.2rem] rotate-0 scale-100 transition-all dark:-rotate-90 dark:scale-0"
          />
          <Icon
              icon="radix-icons:sun"
              class="absolute h-[1.2rem] w-[1.2rem] rotate-90 scale-0 transition-all dark:rotate-0 dark:scale-100"
          />
          <span class="sr-only">Toggle theme</span>
        </Button>
      </TooltipTrigger>
      <TooltipContent>
        <p v-if="colorMode.preference == 'light'">Switch to dark mode</p>
        <p v-else>Switch to light mode</p>
      </TooltipContent>
    </Tooltip>
  </TooltipProvider>
</template>
