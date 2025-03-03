<script setup lang="ts">

import NewEntryDialogue from "~/components/table/NewEntryDialogue.vue";
import { DialogTrigger } from "~/components/ui/dialog";
import { Button } from "~/components/ui/button";

const props = defineProps<{
  name: string;
}>();

const keyFilterSet = {
  name: {
    regex: RegExp("[а-яА-ЯA-Za-z]{1,150}"),
    format: "Строка до 150 символов",
  }
}

const types = ref([])

async function fetchTypes()  {
  types.value = await $fetch("http://localhost:8081/estore/api/" + props.name) as any;

}

onMounted(async () => {
  fetchTypes();
})

</script>

<template>
  <Dialog>
    <DialogTrigger as-child>
      <Button variant="outline">
        Создать {{ props.name }}
      </Button>
    </DialogTrigger>
    <NewEntryDialogue @newEntry="fetchTypes()" :keySet="keyFilterSet" :name="props.name" :endpoint="'/estore/api/' + props.name" />
  </Dialog>
  <div>
    <p>{{ props.name }}
      </p>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead class="w-[100px]">
              id
            </TableHead>
            <TableHead>name</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          <TableRow v-for="type in types" :key="type.id">
            <TableCell class="font-medium">
              {{ type.id }}
            </TableCell>
            <TableCell>{{ type.name }}</TableCell>
          </TableRow>
        </TableBody>
      </Table>
  </div>

</template>

<style scoped>

</style>