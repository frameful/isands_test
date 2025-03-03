<script setup lang="ts">

import { DialogTrigger } from "~/components/ui/dialog";
import { Button } from "~/components/ui/button";
import NewEntryDialogue from "~/components/table/NewEntryDialogue.vue";

const shops = ref([]);

async function fetchShop()  {
  shops.value = await $fetch("http://localhost:8081/estore/api/shop") as any;

}

onMounted(async () => {
  fetchShop();
})

const keyFilterSet = {
  name: {
    regex: new RegExp("[а-яА-Яa-z]{1,150}"),
    format: "Строка до 150 символов"
  },
  address: {
    regex: new RegExp("[а-яА-Яa-z]+"),
    format: "Строка"
  },
}


</script>

<template>
  <Dialog>
    <DialogTrigger as-child>
        <Button variant="outline">
          Создать магазин
        </Button>
    </DialogTrigger>
    <NewEntryDialogue @newEntry="fetchShop()" name="shop" :keySet="keyFilterSet" endpoint="/estore/api/shop" />
  </Dialog>


  <Table>
    <TableHeader>
      <TableRow>
        <TableHead class="w-[100px]">
          id
        </TableHead>
        <TableHead>name</TableHead>
        <TableHead>address</TableHead>
      </TableRow>
    </TableHeader>
    <TableBody>
      <TableRow v-for="shop in shops" :key="shop.id">
        <TableCell class="font-medium">
          {{ shop.id }}
        </TableCell>
        <TableCell>{{ shop.name }}</TableCell>
        <TableCell>{{ shop.address }}</TableCell>
      </TableRow>
    </TableBody>
  </Table>

</template>