<script setup lang="ts">

import NewEntryDialogue from "~/components/table/NewEntryDialogue.vue";
import { DialogTrigger } from "~/components/ui/dialog";
import { Button } from "~/components/ui/button";
import { Icon } from "@iconify/vue";

const props = defineProps<{
  name: string;
}>();

const keyFilterSet = {
  name: {
    regex: RegExp("[а-яА-ЯA-Za-z]{1,150}"),
    format: "Строка до 150 символов",
    name: "Название"
  }
}

function createUpdateDto(type: any) {
  return {
    name: type.name,
  }
}

interface TypeState {
  types: any[];
  page: number;
  pageSize: number;
}

const typeState = reactive<TypeState>({
  types: [],
  page: 0,
  pageSize: 10,
})

async function fetchTypes(page: number)  {
  typeState.types = await $fetch("http://localhost:8081/estore/api/" + props.name + "/?page=" + typeState.page + "&pageSize=" + typeState.pageSize) as any;
}

watch(() => typeState.page,
    (page) => {
      fetchTypes(page);
    }
)

onMounted(async () => {
  await fetchTypes(0);
})

</script>

<template>
  <Dialog>
    <DialogTrigger as-child>
      <Button variant="outline">
        Создать {{ props.name }}
      </Button>
    </DialogTrigger>
    <NewEntryDialogue @newEntry="fetchTypes(typeState.page)" :keySet="keyFilterSet" :name="props.name" :endpoint="'/estore/api/' + props.name" />
  </Dialog>

  <div>
    Страница: {{ typeState.page + 1 }}

    <Button @click="typeState.page--" variant="outline" :disabled="typeState.page == 0">Предыдущая страница</Button>
    -
    <Button @click="typeState.page++" variant="outline" :disabled="typeState.types.length !== typeState.pageSize">Следующая страница</Button>
  </div>

  <div>
    <p>{{ props.name }}
      </p>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead class="w-[100px]">
              id
            </TableHead>
            <TableHead>Название</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          <TableRow v-for="type in typeState.types" :key="type.id">
            <TableCell class="font-medium">
              {{ type.id }}
            </TableCell>
            <TableCell>{{ type.name }}</TableCell>
            <TableCell>
              <Dialog>
                <DialogTrigger>
                  <Button variant="outline">
                    <Icon icon="radix-icons:pencil-1" />
                  </Button>
                </DialogTrigger>
                <NewEntryDialogue editing :item-to-edit="createUpdateDto(type)" @newEntry="fetchTypes(typeState.page)" name="shop" :keySet="keyFilterSet" :endpoint='"/estore/api/" + name + "/" + type.id' />
              </Dialog>
            </TableCell>
          </TableRow>
        </TableBody>
      </Table>
  </div>

</template>

<style scoped>

</style>