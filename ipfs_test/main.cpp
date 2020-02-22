#include <eosio/eosio.hpp>
using namespace eosio;
class [[eosio::contract]] ipfs : public contract {
    public:
        using contract::contract;
        [[eosio::action]] void registe(){
            print("Hello ",get_self());
            print("Hello ",get_first_receiver());
        }

    
    private:
        using contract::contract;
        [[eosio::action]] void encrypt(){
            
        }

        using contract::contract;
        [[eosio::action]] void upload(){
            
        }
    };
EOSIO_DISPATCH( ipfs, (registe)(encrypt)(upload))