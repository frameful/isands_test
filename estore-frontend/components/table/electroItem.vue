<script setup lang="ts">

import NewEntryDialogue from "~/components/table/NewEntryDialogue.vue";
import { DialogTrigger } from "~/components/ui/dialog";
import { Button } from "~/components/ui/button";
import { Icon } from "@iconify/vue";

interface ElectroItemState {
  electroItems: any[];
  page: number;
  pageSize: number;
}

const electroItemState = reactive<ElectroItemState>({
  electroItems: [],
  page: 0,
  pageSize: 10,
})

async function fetchElectroItems(page: number)  {
  electroItemState.electroItems = await $fetch("http://localhost:8081/estore/api/electro_item/?page="
      + electroItemState.page + "&pageSize="
      + electroItemState.pageSize) as any;
}

function createUpdateDto(electroItem: any) {
  return {
    name: electroItem.name,
    description: electroItem.description,
    price: electroItem.price,
    archive: electroItem.archive ? 1 : 0,
    count: electroItem.count,
    electroTypeId: electroItem.electroType.id,
  }
}
watch(() => electroItemState.page,
    (page) => {
      fetchElectroItems(page);
    }
)

onMounted(async () => {
  await fetchElectroItems(0);
  console.log(electroItemState.electroItems.length)
})

const keyFilterSet = {
  name: {
    regex: new RegExp("[а-яА-ЯA-Za-z]{1,150}"),
    format: "Строка до 150 символов",
    name: 'Название'
  },
  price: {
    regex: new RegExp("[0-9]|[1-9][0-9]+"),
    format: "Число",
    name: 'Цена'
  },
  count: {
    regex: new RegExp("[0-9]|[1-9][0-9]+"),
    format: "Число",
    name: 'Количество'
  },
  archive: {
    regex: new RegExp("[0-1]"),
    format: "1 - Да, 0 - Нет",
    name: 'В наличии'
  },
  description: {
    regex: new RegExp("[а-яА-ЯA-Za-z]+"),
    format: "Строка без ограничений",
    name: 'Описание'
  },
  electroTypeId: {
    regex: new RegExp("[0-9]|[1-9][0-9]+"),
    format: "Число",
    name: 'Идентификатор типа товара'
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
    <NewEntryDialogue @newEntry="fetchElectroItems(electroItemState.page)" :keySet="keyFilterSet" name="electro_item" endpoint="/estore/api/electro_item" />
  </Dialog>
  <div class="flex items-center">
    <Button @click="electroItemState.page--" variant="outline" :disabled="electroItemState.page == 0">
      <Icon icon="radix-icons:double-arrow-left" />
    </Button>
    <p class="mx-2">{{ electroItemState.page + 1 }}</p>
    <Button @click="electroItemState.page++" variant="outline" :disabled="electroItemState.electroItems.length !== electroItemState.pageSize">
      <Icon icon="radix-icons:double-arrow-right" />
    </Button>
  </div>
  <Table>
    <TableHeader>
      <TableRow>
        <TableHead class="w-[100px]">
          id
        </TableHead>
        <TableHead>Название</TableHead>
        <TableHead>Цена</TableHead>
        <TableHead class="text-right">
          Количество
        </TableHead>
        <TableHead class="text-center">В наличии</TableHead>
        <TableHead class="text-center">Описание</TableHead>
        <TableHead class="text-center">Тип товаров</TableHead>
      </TableRow>
    </TableHeader>
    <TableBody>
      <TableRow v-for="electroItem in electroItemState.electroItems" :key="electroItem.id">
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
        <TableCell>
          <Dialog>
            <DialogTrigger>
              <Button variant="outline">
                <Icon icon="radix-icons:pencil-1" />
              </Button>
            </DialogTrigger>
            <NewEntryDialogue :itemToEdit="createUpdateDto(electroItem)"
                              :editing="true"
                              @newEntry="fetchElectroItems(electroItemState.page)"
                              :keySet="keyFilterSet"
                              name="purchase" :endpoint="'/estore/api/electro_item/' + electroItem.id" />
          </Dialog>
        </TableCell>
      </TableRow>
    </TableBody>
  </Table>

</template>

<style scoped>

</style>