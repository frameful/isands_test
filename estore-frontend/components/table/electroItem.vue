<script setup lang="ts">

import NewEntryDialogue from "~/components/table/NewEntryDialogue.vue";
import { DialogTrigger } from "~/components/ui/dialog";
import { Button } from "~/components/ui/button";


const electroItems = ref([])

async function fetchElectroItem()  {
  electroItems.value = await $fetch("http://localhost:8081/estore/api/electro_item") as any;

}

onMounted(async () => {
  fetchElectroItem();
})

const keyFilterSet = {
  name: {
    regex: new RegExp("[а-яА-ЯA-Za-z]{1,150}"),
    format: "Строка до 150 символов"
  },
  price: {
    regex: new RegExp("[0-9]|[1-9][0-9]+"),
    format: "Число"
  },
  count: {
    regex: new RegExp("[0-9]|[1-9][0-9]+"),
    format: "Число"
  },
  archive: {
    regex: new RegExp("[0-1]"),
    format: "1 - Да, 0 - Нет"
  },
  description: {
    regex: new RegExp("[а-яА-ЯA-Za-z]+"),
    format: "Строка без ограничений"
  },
  electroTypeId: {
    regex: new RegExp("[0-9]|[1-9][0-9]+"),
    format: "Число"
  }
}



</script>

<template>

  <Dialog>
    <DialogTrigger as-child>
      <Button variant="outline">
        Создать электроприбор
      </Button>
    </DialogTrigger>
    <NewEntryDialogue @newEntry="fetchElectroItem()" :keySet="keyFilterSet" name="electro_item" endpoint="/estore/api/electro_item" />
  </Dialog>

  <Table>
    <TableHeader>
      <TableRow>
        <TableHead class="w-[100px]">
          id
        </TableHead>
        <TableHead>name</TableHead>
        <TableHead>price</TableHead>
        <TableHead class="text-right">
          count
        </TableHead>
        <TableHead class="text-center">archive</TableHead>
        <TableHead class="text-center">description</TableHead>
        <TableHead class="text-center">electroType</TableHead>
      </TableRow>
    </TableHeader>
    <TableBody>
      <TableRow v-for="electroItem in electroItems" :key="electroItem.id">
        <TableCell class="font-medium">
          {{ electroItem.id }}
        </TableCell>
        <TableCell>{{ electroItem.name}}</TableCell>
        <TableCell>{{ electroItem.price}}</TableCell>
        <TableCell class="text-right">
          {{ electroItem.count }}
        </TableCell>
        <TableCell>
            {{ electroItem.archive }}
        </TableCell>
        <TableCell>
          {{ electroItem.description }}
        </TableCell>
        <TableCell>
          {{ electroItem.electroType.name }}
        </TableCell>
      </TableRow>
    </TableBody>
  </Table>

</template>

<style scoped>

</style>